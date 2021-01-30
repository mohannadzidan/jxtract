package com.mou.jxtract.web.methods;

import com.google.gson.JsonElement;
import com.mou.jxtract.core.AbstractExtractor;
import com.mou.jxtract.core.utils.IExtract;
import com.mou.jxtract.core.Method;
import com.mou.jxtract.core.descriptors.MethodDescriptor;
import com.mou.jxtract.core.deserializers.MethodDeserializer;
import com.mou.jxtract.core.utils.ISource;
import com.mou.jxtract.core.utils.Requirements;
import com.mou.jxtract.web.WebExtractor;
import com.mou.jxtract.web.descriptors.ExecuteScriptArguments;
import com.mou.jxtract.web.scripts.WebScript;
import org.jsoup.nodes.Element;

import java.lang.reflect.Type;

public class ExecuteScript extends Method implements IExtract<JsonElement, Element> {

    //private Logger log = Logger.getLogger("ExecuteScript");

    public ExecuteScript(MethodDeserializer methodDeserializer) {
        super(methodDeserializer);
    }

    @Override
    public JsonElement extract(Element src, MethodDescriptor method, AbstractExtractor<JsonElement, Element> extractor) {
        ExecuteScriptArguments args = (ExecuteScriptArguments) method.args;
        Requirements.require(args.script, "script", getType());
        Requirements.require(args.script.getType(), "script.name", getType());
        Requirements.require(args.script.source, "script.scope", getType());
        WebScript webScript = (WebScript) extractor.getScript(args.script.getType());
        @SuppressWarnings("unchecked")
        ISource<Element> scopingFunc = (ISource<Element>) extractor.getMethod(args.script.source.getType());
        return webScript.execute(scopingFunc.source(src, args.script.source), args.script, (WebExtractor) extractor);
    }

    @Override
    public Type getArgumentsType() {
        return ExecuteScriptArguments.class;
    }

    @Override
    public String getType() {
        return "executeScript";
    }
}
