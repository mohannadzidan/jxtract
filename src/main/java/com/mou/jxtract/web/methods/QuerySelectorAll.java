package com.mou.jxtract.web.methods;

import com.google.gson.JsonArray;
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
import java.util.stream.Stream;

public class QuerySelectorAll extends Method implements IExtract<JsonElement, Element> {
    public QuerySelectorAll(MethodDeserializer methodDeserializer) {
        super(methodDeserializer);
    }

    @Override
    public JsonArray extract(Element src, MethodDescriptor method, AbstractExtractor<JsonElement, Element> extractor) {
        QuerySelectorArguments arguments = (QuerySelectorArguments) method.args;
        JsonArray array = new JsonArray();
        Requirements.require(arguments.query, "query", getType());
        Stream<Element> stream = src.select(arguments.query).stream().limit(arguments.limit);
        if (arguments.attribute != null) {
            stream.forEach(e -> array.add(new JsonPrimitive(e.attr(arguments.attribute))));
            return array;
        }
        if (arguments.script != null) {
            WebScript webScript = (WebScript) extractor.getScript(arguments.script.getType());
            if(arguments.script.source != null){
                @SuppressWarnings("unchecked")
                ISource<Element> scopeWeb = (ISource<Element>) extractor.getMethod(arguments.script.source.getType());
                stream.forEach(e -> array.add(webScript.execute(scopeWeb.source(e,
                        arguments.script.source), arguments.script, (WebExtractor) extractor)));
                return array;
            }
            stream.forEach(e -> array.add(webScript.execute(e, arguments.script, (WebExtractor) extractor)));
            return array;
        }
        stream.forEach(e -> array.add(new JsonPrimitive(e.text())));
        return array;
    }

    @Override
    public Type getArgumentsType() {
        return QuerySelectorArguments.class;
    }

    @Override
    public String getType() {
        return "querySelectorAll";
    }
}
