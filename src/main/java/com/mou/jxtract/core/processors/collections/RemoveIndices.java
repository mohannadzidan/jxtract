package com.mou.jxtract.core.processors.collections;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.mou.jxtract.core.PostProcessor;
import com.mou.jxtract.core.descriptors.ProcessorDescriptor;
import com.mou.jxtract.core.deserializers.ProcessorDeserializer;
import com.mou.jxtract.core.utils.Requirements;
import com.mou.jxtract.web.descriptors.RemoveIndicesConfiguration;

import java.lang.reflect.Type;
import java.util.Arrays;

public class RemoveIndices extends PostProcessor<JsonElement> {


    public RemoveIndices(ProcessorDeserializer deserializer) {
        super(deserializer);
    }

    @Override
    public JsonElement apply(JsonElement root, ProcessorDescriptor descriptor) {
        RemoveIndicesConfiguration configuration = (RemoveIndicesConfiguration) descriptor.configuration;
        Requirements.require(configuration.attribute, "attribute", getType());
        Requirements.require(configuration.indices, "indices", getType());
        JsonArray collection = (JsonArray) root.getAsJsonObject().get(configuration.attribute);
        Arrays.sort(configuration.indices);
        for (int i = configuration.indices.length - 1; i >= 0; i--) {
            collection.remove(configuration.indices[i] < 0 ? configuration.indices[i] + collection.size() : configuration.indices[i]);
        }
        return root;
    }


    @Override
    public Type getConfigurationType() {
        return RemoveIndicesConfiguration.class;
    }

    @Override
    public String getType() {
        return "collections.removeIndices";
    }
}
