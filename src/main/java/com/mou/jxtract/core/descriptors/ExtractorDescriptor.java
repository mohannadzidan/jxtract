package com.mou.jxtract.core.descriptors;

public class ExtractorDescriptor {
    private ScriptDescriptor script;
    private ProcessorDescriptor[] postProcessors;

    public ScriptDescriptor getScript() {
        return script;
    }

    public ProcessorDescriptor[] getPostProcessors() {
        return postProcessors;
    }
}
