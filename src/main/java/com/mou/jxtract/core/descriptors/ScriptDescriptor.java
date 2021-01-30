package com.mou.jxtract.core.descriptors;

import com.mou.jxtract.core.StringTyped;

public class ScriptDescriptor extends StringTyped {
    public AttributeDescriptor[] attributes;
    public MethodDescriptor source;
    public String type;

    @Override
    public String getType() {
        return type;
    }
}
