package com.mou.jxtract.web.descriptors;

import com.mou.jxtract.core.descriptors.ArgumentsDescriptor;
import com.mou.jxtract.core.descriptors.ScriptDescriptor;

public class ExecuteScriptArguments implements ArgumentsDescriptor {
    public ScriptDescriptor script;

    public ScriptDescriptor getScript() {
        return script;
    }
}
