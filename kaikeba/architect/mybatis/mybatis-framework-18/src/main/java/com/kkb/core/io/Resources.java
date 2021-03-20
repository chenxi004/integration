package com.kkb.core.io;

import java.io.InputStream;

public class Resources {
    public static InputStream getResourceAsStream(String location){
        return Resources.class.getClassLoader().getResourceAsStream(location);
    }
}
