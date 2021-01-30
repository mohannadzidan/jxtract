package com.mou.jxtract.core.utils;

public interface IBuild<T, K> {
    T build(K from);
}
