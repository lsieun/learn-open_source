package com.lsieun.httpclient;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class B_PostLogin {
    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClientBuilder.create().build();

        String url = "http://mudu.tv/session.php?a=login";
        HttpPost request = new HttpPost(url);

        request.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36\n" +
                "Content-Type: application/x-www-form-urlencoded; charset=UTF-8");
        request.setHeader("Referer","http://mudu.tv/login");
        request.setHeader("Accept-Encoding","gzip, deflate");
        request.setHeader("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8");
        request.setHeader("Cookie","UM_distinctid=1609b03a0d09f-0479b4ec35569c-5f19331c-1fa400-1609b03a0d1207; bad_id263141c0-bee5-11e7-9a18-2bfba5a2cd2b=534d3b31-eb79-11e7-b50b-7344e471242d; _ga=GA1.2.48603381.1514456751; _gid=GA1.2.881447882.1514456751; Hm_lvt_4957c9c61384eec034ff8c86696b2a99=1514461480; stat_mac=9F59ECBB46707057; nice_id263141c0-bee5-11e7-9a18-2bfba5a2cd2b=18a7ea51-ec61-11e7-9c3e-db30a9413a3a; Hm_lvt_22db863c7d7b02b102cf93831ce76aa6=1514429194,1514459034,1514528741,1514529104; sceneId=152148219; PHPSESSID=fh6apk0ttf01v7ckllueoh7ek4; CNZZDATA1261012620=1826462907-1514526570-http%253A%252F%252Fmudu.tv%252F%7C1514526570; Hm_lpvt_22db863c7d7b02b102cf93831ce76aa6=1514529979");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("email","liusen"));
        urlParameters.add(new BasicNameValuePair("password","515882294"));
        urlParameters.add(new BasicNameValuePair("authCode","guv2k"));

        request.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(request);

        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        HeaderIterator headerIterator = response.headerIterator();
        while(headerIterator.hasNext()){
            Header header = headerIterator.nextHeader();
            String headerName = header.getName();
            String headerValue = header.getValue();
            System.out.println(headerName + ": " + headerValue);
        }

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }


        System.out.println(result.toString());

    }
}
