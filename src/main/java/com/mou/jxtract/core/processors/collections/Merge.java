package com.mou.jxtract.core.processors.collections;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mou.jxtract.core.PostProcessor;
import com.mou.jxtract.core.descriptors.ProcessorDescriptor;
import com.mou.jxtract.core.deserializers.ProcessorDeserializer;
import com.mou.jxtract.core.utils.JsonUtils;
import com.mou.jxtract.core.utils.Requirements;
import com.mou.jxtract.web.descriptors.MergeConfiguration;

import java.lang.reflect.Type;

public class Merge extends PostProcessor<JsonElement> {

    public Merge(ProcessorDeserializer deserializer) {
        super(deserializer);
    }

    @Override
    public JsonElement apply(JsonElement data, ProcessorDescriptor descriptor) {
        MergeConfiguration args = (MergeConfiguration) descriptor.configuration;
        Requirements.require(args.destination, "destination", getType());
        Requirements.require(args.map, "map", getType());
        JsonArray collection = new JsonArray();
        for(String attribute : args.map.keySet()){
            JsonArray array = Requirements.requireType(JsonUtils.readAttribute(attribute, data.getAsJsonObject()), attribute, JsonArray.class);
            for(int i =0; i< array.size(); i++){
                if(collection.size() <= i) collection.add(new JsonObject());
                JsonUtils.setAttribute(args.map.get(attribute), array.get(i), collection.get(i).getAsJsonObject());
            }
        }
        JsonUtils.setAttribute(args.destination, collection, data.getAsJsonObject());
        return data;
    }



    @Override
    public Type getConfigurationType() {
        return MergeConfiguration.class;
    }
    @Override
    public String getType() {
        return "collections.merge";
    }
}
