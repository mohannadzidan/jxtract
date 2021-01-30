package com.mou.jxtract.core.descriptors;

import com.mou.jxtract.core.StringTyped;

public class MethodDescriptor extends StringTyped {
    public ArgumentsDescriptor args;
    public String type;

    @Override
    public String getType() {
        return type;
    }
}
