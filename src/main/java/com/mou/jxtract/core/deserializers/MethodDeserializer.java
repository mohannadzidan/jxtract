package com.mou.jxtract.core.deserializers;

import com.google.gson.*;
import com.mou.jxtract.core.utils.TypeRegisterer;
import com.mou.jxtract.core.descriptors.ArgumentsDescriptor;
import com.mou.jxtract.core.descriptors.MethodDescriptor;
import com.mou.jxtract.core.utils.Requirements;

import java.lang.reflect.Type;
import java.util.HashMap;

public class MethodDeserializer implements JsonDeserializer<MethodDescriptor>, TypeRegisterer {

    protected final HashMap<String, Type> argumentDescriptors = new HashMap<>();

    @Override
    public MethodDescriptor deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        String methodType = Requirements.requireOrThrow(object.get("type"), new JsonParseException("The type of the method must be defined.")).getAsString();
        Type argumentsType = Requirements.requireOrThrow(argumentDescriptors.get(methodType), new JsonParseException("Not supported method " + methodType));
        JsonElement argsJson = Requirements.requireOrThrow(object.get("args"), new JsonParseException(methodType + " requires an arguments object \"args\"."));
        ArgumentsDescriptor args = jsonDeserializationContext.deserialize(argsJson, argumentsType);
        MethodDescriptor descriptor = new MethodDescriptor();
        descriptor.args = args;
        descriptor.type = methodType;
        return descriptor;
    }

    @Override
    public void register(String name, Type type) {
        argumentDescriptors.put(name, type);
    }
}
