package com.company.interview.merge;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anita on 29-May-16.
 */
@RequiredArgsConstructor
public class Args {
    private final Map<String, String> argMap;

    public static Args parse(String[] args) {
        Map<String, String> argMap = new HashMap<>();
        for (String arg : args) {
            int equals = arg.indexOf('=');
            if (equals != -1) {
                String key = arg.substring(0, equals);
                String value = arg.substring(equals + 1);
                argMap.put(key, value);
            }
        }
        return new Args(argMap);
    }

    String getString(String key, String defaultValue) {
        return argMap.getOrDefault(key, defaultValue);
    }

    boolean getBoolean(String key, boolean defaultValue) {
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
