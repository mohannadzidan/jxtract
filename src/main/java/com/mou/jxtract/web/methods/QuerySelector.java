package com.mou.jxtract.web.methods;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.mou.jxtract.core.AbstractExtractor;
import com.mou.jxtract.core.utils.IExtract;
import com.mou.jxtract.core.Method;
import com.mou.jxtract.core.descriptors.MethodDescriptor;
import com.mou.jxtract.core.deserializers.MethodDeserializer;
import com.mou.jxtract.core.utils.ISource;
import com.mou.jxtract.core.utils.Requirements;
import com.mou.jxtract.web.WebExtractor;
import com.mou.jxtract.web.descriptors.QuerySelectorArguments;
import com.mou.jxtract.web.scripts.WebScript;
import org.jsoup.nodes.Element;

import java.lang.reflect.Type;

public class QuerySelector extends Method implements IExtract<JsonElement, Element>, ISource<Element> {
    public QuerySelector(MethodDeserializer methodDeserializer) {
        super(methodDeserializer);
    }

    @Override
    public JsonElement extract(Element src, MethodDescriptor method, AbstractExtractor<JsonElement, Element> extractor) {
        QuerySelectorArguments arguments = (QuerySelectorArguments) method.args;
        Requirements.require(arguments.query, "query", getType());
        Element element = src.select(arguments.query).first();
        if(arguments.attribute != null){
            return new JsonPrimitive(element.attr(arguments.attribute));
        }
        if(arguments.script != null){
            WebScript webScript = (WebScript) extractor.getScript(arguments.script.getType());
            if(arguments.script.source != null){
                @SuppressWarnings("unchecked")
                ISource<Element> scopingFunc = (ISource<Element>) extractor.getMethod(arguments.script.getType());
                return webScript.execute(scopingFunc.source(element, arguments.script.source), arguments.script, (WebExtractor) extractor);
            }
            return webScript.execute(element, arguments.script, (WebExtractor) extractor);
        }
        return new JsonPrimitive(element.text());
    }

    @Override
    public Element source(Element source, MethodDescriptor methodDescriptor) {
        QuerySelectorArguments arguments = (QuerySelectorArguments) methodDescriptor.args;
        Requirements.require(arguments.query, "query", getType());
        return source.select(arguments.query).first();
    }

    @Override
    public Type getArgumentsType() {
        return QuerySelectorArguments.class;
    }

    @Override
    public String getType() {
        return "querySelector";
    }
}
