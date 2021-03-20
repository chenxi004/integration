package com.kkb.core.utils;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

public class DocumentUtils {
    public static Document createDocument(InputStream inputStream){
        try {
            SAXReader saxReader = new SAXReader();
            return saxReader.read(inputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
