package com.lsieun.httpclient;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class A_GetHTMLContent {
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_URL = "https://www.webpagefx.com/tools/emoji-cheat-sheet/";

    public static void main(String[] args) throws IOException {
        //1、创建HttpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        System.out.println("创建HttpClient...");

        //2、创建HttpRequest请求
        HttpGet httpGet = new HttpGet(GET_URL);
        httpGet.addHeader("User-Agent", USER_AGENT);
        System.out.println("创建请求...");

        //3、接收返回结果HttpResponse
        System.out.println("发送请求...");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        System.out.println("返回结果成功...");

        //4、对结果进行处理
        System.out.println("处理结果...");
        //(4-1)处理StatusLine
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        ProtocolVersion protocolVersion = statusLine.getProtocolVersion();
        String reasonPhrase = statusLine.getReasonPhrase();
        System.out.println("GET Response Status: " + statusCode);
        System.out.println("GET Protocol Version: " + protocolVersion.toString());
        System.out.println("GET Protocol Version: " + reasonPhrase);

        //(4-2)处理Header
        HeaderIterator headerIterator = response.headerIterator();
        while(headerIterator.hasNext()){
            Header header = headerIterator.nextHeader();
            String headerName = header.getName();
            String headerValue = header.getValue();
            System.out.println(headerName + ": " + headerValue);
        }

        //(4-3)处理HttpEntity
        HttpEntity entity = response.getEntity();
        StringBuffer sb = new StringBuffer();
        if(entity != null){
            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                //sb.append(inputLine + "\r\n");
                sb.append(inputLine);
            }
            reader.close();
            System.out.println(sb.toString());
        }
        else{
            System.out.println("获取不到内容");
        }

        //5、关闭资源连接
        System.out.println("关闭HttpClient连接...");
        response.close();
        httpClient.close();
        System.out.println("执行结束...");
    }

}
