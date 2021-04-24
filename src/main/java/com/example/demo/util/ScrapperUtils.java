package com.example.demo.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isEmpty;

public class ScrapperUtils {

    private ScrapperUtils(){}

    public static boolean isFile(String url) {
        return findFirst(url, 0, "/blob/") > -1 && findFirst(url, 0, "/tree/") == -1;
    }

    public static int findFirst(String text, int pos, String key){
        return  text.indexOf(key, pos);
    }

    public static List<Integer> findMatches(String text, String key) {
        List<Integer> matches = new ArrayList<>();
        int index = 0;
        if (isEmpty(text) || isEmpty(key)) {
            return Collections.emptyList();
        }
        while (true) {
            index = text.indexOf(key, index);
            if (index != -1) {
                matches.add(index);
                index += key.length();
            }
            else {
                break;
            }
        }
        return matches;
    }

    public static String getExtension(String url) {
        String filename = url.substring(url.lastIndexOf("/") + 1);
        int periodPosition = filename.lastIndexOf(".");
        boolean hasExtension = periodPosition > -1;
        return hasExtension ? filename.substring(periodPosition + 1) : filename;
    }

}
