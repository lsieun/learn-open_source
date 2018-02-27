package com.lsieun.demo;

import java.io.IOException;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;


public class Demo07 {
    public static void main(String[] args) throws IOException {
        StringEntity myEntity = new StringEntity("important message",
                ContentType.create("text/plain", "UTF-8"));
        System.out.println(myEntity.getContentType());
        System.out.println(myEntity.getContentLength());
        System.out.println(EntityUtils.toString(myEntity));
        System.out.println(EntityUtils.toByteArray(myEntity).length);
    }
}
