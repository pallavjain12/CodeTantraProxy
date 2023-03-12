package com.example.codetantraproxy.Helper;

import java.util.HashMap;

public class StringHelper {
    static HashMap<Character, String> map = new HashMap<Character, String>();
    {
        map.put('@',"%40");
        map.put('#',"%23");
        map.put('$',"%24");
        map.put('%',"%25");
        map.put('^',"%5E");
        map.put('&',"%26");
        map.put(' ',"+");
        map.put('=',"%3D");
        map.put('+',"%2B");
        map.put(',',"%2C");
        map.put('<',"%3C");
        map.put('>',"%3E");
        map.put('/',"%2F");
        map.put('?',"%3F");
    }
    public static String convertToURLSafe(String str) {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {

        }
        return "";
    }
}
