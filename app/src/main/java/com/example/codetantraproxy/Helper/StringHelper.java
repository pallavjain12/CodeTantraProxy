package com.example.codetantraproxy.Helper;

import java.util.HashMap;

public class StringHelper {
    static final HashMap<Character, String> map = new HashMap<>();
    public static String convertToURLSafe(String str) {
        StringBuilder sbr = new StringBuilder();
        map.put('@', "%40");
        map.put('#', "%23");
        map.put('$', "%24");
        map.put('%', "%25");
        map.put('^', "%5E");
        map.put('&', "%26");
        map.put('+', "%2B");
        map.put('=', "%3D");
        map.put('{', "%7B");
        map.put('}', "%7D");
        map.put('|', "%7C");
        map.put('[', "%5B");
        map.put(']', "%5D");
        map.put('\\', "%5C");
        map.put(':', "%3A");
        map.put('"', "%22");
        map.put('<', "%3C");
        map.put('>', "%3E");
        map.put('?', "%3F");
        map.put(';', "%3B");
        map.put(',', "%2C");
        map.put('/', "%2F");
        map.put(' ', "+");
        for (char i : str.toCharArray()) {
            if (map.containsKey(i)) {
                sbr.append(map.get(i));
            }
            else {
                sbr.append(i);
            }
        }
        return sbr.toString();
    }
}
