package com.mou.jxtract.core.processors.collections;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.mou.jxtract.core.PostProcessor;
import com.mou.jxtract.core.descriptors.ProcessorDescriptor;
import com.mou.jxtract.core.deserializers.ProcessorDeserializer;
import com.mou.jxtract.core.utils.JsonUtils;
import com.mou.jxtract.core.utils.Requirements;
import com.mou.jxtract.web.descriptors.FilterConfiguration;

import java.lang.reflect.Type;
import java.util.Objects;

public class Filter extends PostProcessor<JsonElement> {

    public Filter(ProcessorDeserializer deserializer) {
        super(deserializer);
    }

    @Override
    public Type getConfigurationType() {
        return FilterConfiguration.class;
    }

    public boolean test(JsonElement object, FilterConfiguration config){
        Requirements.require(config.regex, "regex", this);
        if(config.access != null){
            object = JsonUtils.readAttribute(config.access, object.getAsJsonObject());
        }
        Requirements.requireType(object, config.attribute+ Objects.requireNonNullElse(config.access, "."+config.access), JsonPrimitive.class);
        return object.getAsString().matches(config.regex);

    }

    @Override
    public JsonElement apply(JsonElement data, ProcessorDescriptor descriptor) {
        FilterConfiguration config = (FilterConfiguration) descriptor.configuration;
        Requirements.require(config.attribute, "attribute", this);
        JsonArray target = Requirements.requireType(JsonUtils.readAttribute(config.attribute, data.getAsJsonObject()), config.attribute, JsonArray.class);
        JsonArray newTarget = new JsonArray();
        target.forEach(e -> {
            if(test(e, config)) newTarget.add(e);
        });
        JsonUtils.setAttribute(config.attribute, newTarget, data.getAsJsonObject());
        return data;
    }

    @Override
    public String getType() {
        return "collections.filter";
    }
}
