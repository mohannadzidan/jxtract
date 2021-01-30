package com.mou.jxtract.web.descriptors;

public class UrlSourceArguments extends QuerySelectorArguments {
    public String url;
    public Proxy[] proxies;

    public static class Proxy{
        public String port, ip;

        @Override
        public String toString() {
            return ip +":" +port;
        }
    }
}