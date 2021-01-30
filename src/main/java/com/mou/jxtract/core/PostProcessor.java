package com.mou.jxtract.core;

import com.mou.jxtract.core.descriptors.ProcessorDescriptor;
import com.mou.jxtract.core.deserializers.ProcessorDeserializer;

import java.lang.reflect.Type;

public abstract class PostProcessor<T> extends StringTyped {

    public PostProcessor(ProcessorDeserializer deserializer){
        deserializer.register(getType(), getConfigurationType());
    }
    public abstract Type getConfigurationType();
    public abstract T apply(T data, ProcessorDescriptor descriptor);

}
