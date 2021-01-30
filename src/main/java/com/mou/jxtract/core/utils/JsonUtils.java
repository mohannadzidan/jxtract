package com.mou.jxtract.core.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public final class JsonUtils {
    private JsonUtils() {
    }

    public static JsonElement readAttribute(String attribute, JsonObject from){
        String[] path = attribute.split("\\.");
        JsonElement parent = from;
        for(String attr : path){
            if(!parent.isJsonObject())
                throw new RuntimeException(String.format("invalid type of %s expecting JsonObject but found %s", attr, parent.getClass().getTypeName()));
            if(!parent.getAsJsonObject().has(attr)){
                return null;
            }
            parent = parent.getAsJsonObject().get(attr);
        }
        return parent;
    }

    public static JsonElement createIfAbsent(String attribute, JsonObject from){
        String[] path = attribute.split("\\.");
        JsonElement parent = from;
        for(String attr : path){
            if(!parent.isJsonObject())
                throw new RuntimeException(String.format("invalid type of %s expecting JsonObject or null but found %s", attr, parent.getClass().getTypeName()));
            if(!parent.getAsJsonObject().has(attr))
                parent.getAsJsonObject().add(attr, new JsonObject());
            parent = parent.getAsJsonObject().get(attr);
        }
        return parent;
    }
    public static void setAttribute(String attribute, JsonElement value, JsonObject to){
        int x = attribute.lastIndexOf('.');
        if(x > 0){
            createIfAbsent(attribute.substring(0, x), to).getAsJsonObject()
                    .add(attribute.substring(x), value);
        }else{
            to.add(attribute, value);
        }
    }
    public static void removeAttribute(String attribute, JsonObject from){
        int x = attribute.lastIndexOf('.');
        if(x > 0){
            createIfAbsent(attribute.substring(0, x), from).getAsJsonObject()
                    .remove(attribute.substring(x));
        }else{
            from.remove(attribute);
        }
    }
    public static <T> List<T> convertArray(JsonArray array, Class<T> type) {
        ArrayList<T> list = new ArrayList<>();
        String jsonArrayType;
        array.forEach(o -> list.add(type.cast(o)));
        return list;
    }
}
