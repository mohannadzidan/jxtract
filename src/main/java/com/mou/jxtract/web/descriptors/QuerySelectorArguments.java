package com.mou.jxtract.web.descriptors;

import com.mou.jxtract.core.descriptors.ArgumentsDescriptor;
import com.mou.jxtract.core.descriptors.ScriptDescriptor;

public class QuerySelectorArguments implements ArgumentsDescriptor {
    public int limit = Integer.MAX_VALUE;
    public String query;
    public String attribute;
    public ScriptDescriptor script;
}