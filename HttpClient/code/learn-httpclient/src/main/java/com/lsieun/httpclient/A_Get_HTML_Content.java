package com.lsieun.httpclient;


import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class A_Get_HTML_Content {
    private static final String USER_AGENT = "Mozilla/5.0";
    //private static final String GET_URL = "http://www.baidu.com";
    private static final String GET_URL = "https://www.webpagefx.com/tools/emoji-cheat-sheet/";

    public static void main(String[] args) throws Exception{
        //获取HTML页面
        String html = getHtml();
        Document doc = Jsoup.parse(html);
        //Element content = doc.getElementById("wrapper");//baidu
        Element content = doc.getElementById("content");//emoji
        Elements links = content.getElementsByTag("a");
        for (Element link : links) {
            String linkHref = link.attr("href");
            String linkText = link.text();
            System.out.println(linkText + "\t" + linkHref);
        }
    }

    private static String getHtml() throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(GET_URL);
        httpGet.addHeader("User-Agent", USER_AGENT);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        ProtocolVersion protocolVersion = statusLine.getProtocolVersion();
        String reasonPhrase = statusLine.getReasonPhrase();
        System.out.println("GET Response Status: " + statusCode);
        System.out.println("GET Protocol Version: " + protocolVersion.toString());
        System.out.println("GET Protocol Version: " + reasonPhrase);
        HttpEntity entity = response.getEntity();
        StringBuffer sb = new StringBuffer();
        if(entity != null){
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                //sb.append(inputLine + "\r\n");
                sb.append(inputLine);
            }
            reader.close();
            System.out.println(sb.toString());
        }
        response.close();
        httpClient.close();

        return sb.toString();
    }
}
