package com.mou.jxtract.core;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mou.jxtract.core.processors.attributes.Remove;
import com.mou.jxtract.core.processors.collections.Filter;
import com.mou.jxtract.core.processors.collections.Merge;
import com.mou.jxtract.core.processors.collections.RemoveIndices;

import java.util.ArrayList;
import java.util.List;

public abstract class JsonOutputExtractor<T> extends AbstractExtractor<JsonElement, T> {

    public JsonOutputExtractor(GsonBuilder builder, String descriptorJson) {
        super(builder, descriptorJson);
    }
    public JsonOutputExtractor(GsonBuilder builder) {
        super(builder);
    }
    @Override
    public JsonElement build(T from) {
        return null;
    }

    @Override
    protected List<PostProcessor<JsonElement>> getSupportedPostProcessors() {
        ArrayList<PostProcessor<JsonElement>> processors = new ArrayList<>();
        processors.add(new RemoveIndices(getProcessorDeserializer()));
        processors.add(new Merge(getProcessorDeserializer()));
        processors.add(new Remove(getProcessorDeserializer()));
        processors.add(new Filter(getProcessorDeserializer()));
        return processors;
    }
}
