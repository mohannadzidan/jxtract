open module com.mou.jxtract{
    requires transitive com.google.gson;
    requires transitive org.jsoup;
    exports com.mou.jxtract.core;
    exports com.mou.jxtract.core.descriptors;
    exports com.mou.jxtract.core.processors.attributes;
    exports com.mou.jxtract.core.processors.collections;
    exports com.mou.jxtract.core.deserializers;
    exports com.mou.jxtract.core.utils;
    exports com.mou.jxtract.web;
}




