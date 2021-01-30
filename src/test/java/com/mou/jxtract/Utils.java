package com.mou.jxtract;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public final class Utils {

    private Utils() {
    }

    public static String getString(ClassLoader classLoader, String resourcePath) {
        File file = new File(classLoader.getResource(resourcePath).getFile());
        try {
            return new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
