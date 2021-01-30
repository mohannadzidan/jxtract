package com.mou.jxtract.web.scripts;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mou.jxtract.core.AbstractExtractor;
import com.mou.jxtract.core.ExecutableScript;
import com.mou.jxtract.core.utils.IExtract;
import com.mou.jxtract.core.descriptors.AttributeDescriptor;
import com.mou.jxtract.core.descriptors.ScriptDescriptor;
import org.jsoup.nodes.Element;

import java.util.logging.Logger;

public class WebScript extends ExecutableScript<JsonElement, Element> {

    private Logger log = Logger.getAnonymousLogger();

    @Override
    @SuppressWarnings("unchecked")
    public JsonElement execute(Element source, ScriptDescriptor descriptor, AbstractExtractor<JsonElement, Element> extractor) {
        if (descriptor.attributes != null) {
            JsonObject object = new JsonObject();
            for (AttributeDescriptor attributeDescriptor : descriptor.attributes) {
                IExtract<JsonElement, Element> method = (IExtract<JsonElement, Element>) extractor.getMethod(attributeDescriptor.method.getType());
                object.add(attributeDescriptor.name, method.extract(source, attributeDescriptor.method, extractor));
            }
            return object;
        } else {
            return new JsonPrimitive(source.text());
        }
    }

    @Override
    public String getType() {
        return "jxtract.web";
    }
}
