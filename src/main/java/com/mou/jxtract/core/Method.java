package com.mou.jxtract.core;

import com.mou.jxtract.core.deserializers.MethodDeserializer;

import java.lang.reflect.Type;

public abstract class Method extends StringTyped {
    public Method(MethodDeserializer methodDeserializer){
        methodDeserializer.register(getType(), getArgumentsType());
    }
    public abstract Type getArgumentsType();
}
