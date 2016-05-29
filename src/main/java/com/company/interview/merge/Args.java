package com.company.interview.merge;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class Args {
    private final Map<String, String> argMap;

    public static Args parse(String[] args) {
        Map<String, String> argMap = new HashMap<>();
        for (String arg : args) {
            if (!arg.startsWith("--")) {
                continue;
            }
            int equals = arg.indexOf('=');
            if (equals != -1) {
                String key = arg.substring(2, equals);
                String value = arg.substring(equals + 1);
                argMap.put(key, value);
            } else {
                argMap.put(arg.substring(2), "");
            }
        }
        return new Args(argMap);
    }

    public String getString(String key, String defaultValue) {
        return argMap.getOrDefault(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String val = argMap.get(key);
        if (val == null) {
            return defaultValue;
        } else if (val.equals("")) {
            return true;
        } else {
            return Boolean.valueOf(val);
        }
    }

}
