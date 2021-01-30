package com.mou.jxtract.core.processors.attributes;

import com.google.gson.JsonElement;
import com.mou.jxtract.core.PostProcessor;
import com.mou.jxtract.core.descriptors.ProcessorDescriptor;
import com.mou.jxtract.core.deserializers.ProcessorDeserializer;
import com.mou.jxtract.core.utils.JsonUtils;
import com.mou.jxtract.core.utils.Requirements;
import com.mou.jxtract.web.descriptors.RemoveConfiguration;

import java.lang.reflect.Type;

public class Remove extends PostProcessor<JsonElement> {
    public Remove(ProcessorDeserializer deserializer) {
        super(deserializer);
    }

    @Override
    public JsonElement apply(JsonElement data, ProcessorDescriptor descriptor) {
        RemoveConfiguration configuration = (RemoveConfiguration) descriptor.configuration;
        Requirements.require(configuration.attribute, "attribute", getType());
        JsonUtils.removeAttribute(configuration.attribute, data.getAsJsonObject());
        return data;
    }

    @Override
    public Type getConfigurationType() {
        return RemoveConfiguration.class;
    }

    @Override
    public String getType() {
        return "attributes.remove";
    }
}
