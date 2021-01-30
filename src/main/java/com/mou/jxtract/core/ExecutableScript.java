package com.mou.jxtract.core;

import com.mou.jxtract.core.descriptors.ScriptDescriptor;

public abstract class ExecutableScript<T, K> extends StringTyped {
    public abstract T execute(K source, ScriptDescriptor descriptor, AbstractExtractor<T, K> extractor);
}
