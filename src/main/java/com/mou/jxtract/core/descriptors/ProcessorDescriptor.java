package com.mou.jxtract.core.descriptors;

import com.mou.jxtract.core.StringTyped;

public class ProcessorDescriptor extends StringTyped {
    public String type;
    public ArgumentsDescriptor configuration;

    @Override
    public String getType() {
        return type;
    }
}
