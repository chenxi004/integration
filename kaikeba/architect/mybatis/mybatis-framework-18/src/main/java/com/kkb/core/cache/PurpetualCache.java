package com.kkb.core.cache;

import java.util.HashMap;
import java.util.Map;

public class PurpetualCache implements Cache {
    private Map<String,Object> cache =new HashMap<>();
    @Override
    public Object get(String key) {
        return cache.get(key);
    }

    @Override
    public void put(String key, Object value) {
        cache.put(key,value);
    }
}
