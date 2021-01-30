package com.mou.jxtract.core.deserializers;

import com.google.gson.*;
import com.mou.jxtract.core.utils.TypeRegisterer;
import com.mou.jxtract.core.descriptors.ArgumentsDescriptor;
import com.mou.jxtract.core.descriptors.ProcessorDescriptor;
import com.mou.jxtract.core.utils.Requirements;

import java.lang.reflect.Type;
import java.util.HashMap;

public class ProcessorDeserializer implements JsonDeserializer<ProcessorDescriptor>, TypeRegisterer {

    protected final HashMap<String, Type> argumentDescriptors = new HashMap<>();

    @Override
    public ProcessorDescriptor deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        String processorType = Requirements.requireOrThrow(object.get("type"), new JsonParseException("The type of the processor must be defined.")).getAsString();
        Type argumentsType = Requirements.requireOrThrow(argumentDescriptors.get(processorType), new JsonParseException("Not supported processor " + processorType));
        JsonElement argsJson = Requirements.requireOrThrow(object.get("configuration"), new JsonParseException(processorType + " requires an arguments object \"args\"."));
        ArgumentsDescriptor arguments = jsonDeserializationContext.deserialize(argsJson, argumentsType);
        ProcessorDescriptor descriptor = new ProcessorDescriptor();
        descriptor.type = processorType;
        descriptor.configuration = arguments;
        return descriptor;
    }


    @Override
    public void register(String name, Type type) {
        argumentDescriptors.put(name, type);
    }
}
