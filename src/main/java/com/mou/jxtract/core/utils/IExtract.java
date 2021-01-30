package com.mou.jxtract.core.utils;

import com.mou.jxtract.core.AbstractExtractor;
import com.mou.jxtract.core.descriptors.MethodDescriptor;

public interface IExtract<T, K> {
    T extract(K src, MethodDescriptor method, AbstractExtractor<T, K> extractor);
}
