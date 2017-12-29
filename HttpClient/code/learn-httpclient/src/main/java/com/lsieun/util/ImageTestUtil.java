package com.lsieun.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import sun.misc.BASE64Encoder;

public class ImageTestUtil {
    /** <p>图片转化成base64字符串</p>
     *  处理流程：图片-->字节数组-->(Base64编码处理)字符串
     * @param imgFile 图片路径(待处理的图片) 例如：String imgFile = "d://test.jpg";
     * @return (Base64编码处理)字符串
     */
    public static String GetImageStr(String imgFile){
        InputStream in = null;
        byte[] data = null; //读取图片字节数组
        try{
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            int count = in.read(data);
            System.out.println("read file bytes: " + count + "byte");
        }catch (IOException e){
            e.printStackTrace();
        }
        finally{
            IOUtils.closeQuietly(in);
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        if(data == null) return null;
        //返回Base64编码过的字符串
        return encoder.encode(data);
    }
}
