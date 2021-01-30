package com.mou.jxtract;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mou.jxtract.web.WebExtractor;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

class WebExtractorTest {
    @Test
    public void extractFromFushaar(){
        String json = Utils.getString(getClass().getClassLoader(), "fushaar.json");
        String html = Utils.getString(getClass().getClassLoader(), "fushaar.html");
        WebExtractor extractor = new WebExtractor(json);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(extractor.extract(Jsoup.parse(html))));
    }

    @Test
    public void extractFromSanfoundry(){
        String json = Utils.getString(getClass().getClassLoader(), "sanfoundry.json");
        String html = Utils.getString(getClass().getClassLoader(), "sanfoundry.html");
        WebExtractor extractor = new WebExtractor(json);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(extractor.extract(Jsoup.parse(html))));
    }
}