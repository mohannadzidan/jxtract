package com.mou.jxtract.core.utils;

import com.mou.jxtract.core.descriptors.MethodDescriptor;

public interface ISource<T> {
    T source(T source, MethodDescriptor methodDescriptor);
}
