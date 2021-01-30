package com.mou.jxtract.web.sources;

import com.mou.jxtract.core.Method;
import com.mou.jxtract.core.descriptors.MethodDescriptor;
import com.mou.jxtract.core.deserializers.MethodDeserializer;
import com.mou.jxtract.core.utils.ISource;
import com.mou.jxtract.core.utils.Requirements;
import com.mou.jxtract.web.descriptors.UrlSourceArguments;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Url extends Method implements ISource<Element> {

    private static final Logger log = Logger.getLogger("jdds.sources.url");

    public Url(MethodDeserializer methodDeserializer) {
        super(methodDeserializer);
    }

    @Override
    public Element source(Element source, MethodDescriptor methodDescriptor) {
        UrlSourceArguments arguments = (UrlSourceArguments) methodDescriptor.args;
        Requirements.require(arguments.url, "url", getType());
        return getFromUrl(arguments.url, arguments.proxies);
    }


    private Element getFromUrl(String url, UrlSourceArguments.Proxy[] proxies){
        Connection connection = Jsoup.connect(url)
        .header("Cache-Control", "no-cache")
        .header("Pragma:no", "no-cache")
        .userAgent("Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405");
        Element scope = null;
        log.info("connecting to " + url);
        if(proxies!= null){
            List<UrlSourceArguments.Proxy> shuffledSet = Arrays.stream(proxies).collect(Collectors.toList());
            Collections.shuffle(shuffledSet);
            for (UrlSourceArguments.Proxy proxy : shuffledSet) {
                try {
                    log.info(String.format("trying send GET using proxy %s", proxy));
                    scope = connection.proxy(proxy.ip, Integer.parseInt(proxy.port)).get();
                    break;
                } catch (IOException e) {
                    log.warning(e.getClass().getSimpleName()+":"+e.getMessage()+" while using " + proxy);
                    //throw new RuntimeException(e);
                }
            }
            if(scope != null){
                log.info("Success 200");
            }else{
                log.info("All proxies failed!");
            }
        }else{
            try {
                return connection.get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return scope;

    }
    @Override
    public Type getArgumentsType() {
        return UrlSourceArguments.class;
    }
    @Override
    public String getType() {
        return "url";
    }
}
