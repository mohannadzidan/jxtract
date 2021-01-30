package com.mou.jxtract.web;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mou.jxtract.core.ExecutableScript;
import com.mou.jxtract.core.JsonOutputExtractor;
import com.mou.jxtract.core.Method;
import com.mou.jxtract.core.descriptors.ScriptDescriptor;
import com.mou.jxtract.core.utils.ISource;
import com.mou.jxtract.web.methods.ExecuteScript;
import com.mou.jxtract.web.methods.QuerySelector;
import com.mou.jxtract.web.methods.QuerySelectorAll;
import com.mou.jxtract.web.scripts.WebScript;
import com.mou.jxtract.web.sources.Url;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class WebExtractor extends JsonOutputExtractor<Element> {

    public WebExtractor(GsonBuilder builder, String guideJson) {
        super(builder, guideJson);
    }
    public WebExtractor(String guideJson) {
        super(new GsonBuilder(), guideJson);
    }

    public WebExtractor() {
        super(new GsonBuilder());
    }
    @Override
    protected List<Method> getSupportedMethods() {
        List<Method> methods = new ArrayList<>();
        methods.add(new QuerySelector(getMethodDeserializer()));
        methods.add(new QuerySelectorAll(getMethodDeserializer()));
        methods.add(new ExecuteScript(getMethodDeserializer()));
        methods.add(new Url(getMethodDeserializer()));
        return methods;
    }

    @Override
    protected List<ExecutableScript<JsonElement, Element>> getSupportedScripts() {
        List<ExecutableScript<JsonElement, Element>> scripts = new ArrayList<>();
        scripts.add(new WebScript());
        return scripts;
    }

    @Override
    public JsonElement build(Element source) {
        ScriptDescriptor scriptDescriptor = getGuide().getScript();
        WebScript script = (WebScript) getScript(scriptDescriptor.getType());
        if (scriptDescriptor.source != null) {
            @SuppressWarnings("unchecked")
            ISource<Element> scopingMethod = (ISource<Element>) getMethod(scriptDescriptor.source.getType());
            return script.execute(scopingMethod.source(source, scriptDescriptor.source), scriptDescriptor, this);
        }
        return script.execute(source, scriptDescriptor, this);
    }


}
