package com.mou.jxtract.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mou.jxtract.core.descriptors.ExtractorDescriptor;
import com.mou.jxtract.core.descriptors.MethodDescriptor;
import com.mou.jxtract.core.descriptors.ProcessorDescriptor;
import com.mou.jxtract.core.deserializers.MethodDeserializer;
import com.mou.jxtract.core.deserializers.ProcessorDeserializer;
import com.mou.jxtract.core.utils.IBuild;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractExtractor<T, K> implements IBuild<T, K> {
    private final List<Method> methods;
    private final Map<String, ExecutableScript<T, K>> scripts;
    private final List<PostProcessor<T>> postProcessors;
    private final MethodDeserializer methodDeserializer = new MethodDeserializer();
    private final ProcessorDeserializer processorDeserializer = new ProcessorDeserializer();
    private final Gson descriptorDeserializer;
    private ExtractorDescriptor descriptor;
    public AbstractExtractor(GsonBuilder builder, String jsonDescriptor) {
        methods = getSupportedMethods();
        postProcessors = getSupportedPostProcessors();
        this.scripts = getSupportedScripts().stream().collect(Collectors.toMap(ExecutableScript::getType, s -> s));
        builder.registerTypeAdapter(MethodDescriptor.class, methodDeserializer);
        builder.registerTypeAdapter(ProcessorDescriptor.class, processorDeserializer);
        this.descriptorDeserializer = builder.create();
        this.descriptor = descriptorDeserializer.fromJson(jsonDescriptor, ExtractorDescriptor.class);
    }

    public AbstractExtractor(GsonBuilder builder) {
        methods = getSupportedMethods();
        postProcessors = getSupportedPostProcessors();
        this.scripts = getSupportedScripts().stream().collect(Collectors.toMap(ExecutableScript::getType, s -> s));
        builder.registerTypeAdapter(MethodDescriptor.class, methodDeserializer);
        builder.registerTypeAdapter(ProcessorDescriptor.class, processorDeserializer);
        this.descriptorDeserializer = builder.create();
    }

    protected T onPostProcessing(T collections) {
        if (descriptor.getPostProcessors() != null)
            for (ProcessorDescriptor processorDescriptor : descriptor.getPostProcessors()) {
                PostProcessor<T> processor = postProcessors.stream().filter(o -> o.getType().equals(processorDescriptor.type))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Processor not supported " + processorDescriptor.type));
                processor.apply(collections, processorDescriptor);
            }
        return collections;
    }

    public T extract(K source) {
        if(descriptor == null){
            throw new RuntimeException("The descriptor is null, attach a descriptor to this extractor using setDescriptor()");
        }
        return onPostProcessing(build(source));
    }


    protected abstract List<Method> getSupportedMethods();

    protected abstract List<PostProcessor<T>> getSupportedPostProcessors();

    protected abstract List<ExecutableScript<T, K>> getSupportedScripts();

    public Method getMethod(String methodType) {
        return methods.stream().filter(o -> o.getType().equals(methodType))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Method not supported " + methodType));
    }

    public final  ExecutableScript<T, K> getScript(String name){
        ExecutableScript<T, K> s = scripts.get(name);
        if(s == null)
            throw new RuntimeException("Script not supported " + name);
        return s;
    }
    public final ExtractorDescriptor getGuide() {
        return descriptor;
    }

    public final MethodDeserializer getMethodDeserializer() {
        return methodDeserializer;
    }

    public final ProcessorDeserializer getProcessorDeserializer() {
        return processorDeserializer;
    }

    public final void setDescriptor(String jsonDescriptor){
        this.descriptor = descriptorDeserializer.fromJson(jsonDescriptor, ExtractorDescriptor.class);
    }
}
