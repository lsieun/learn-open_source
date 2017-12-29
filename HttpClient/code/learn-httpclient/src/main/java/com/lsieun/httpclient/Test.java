package com.lsieun.httpclient;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.GsonBuilder;
import com.lsieun.util.ImageTestUtil;

public class Test {
    public static void main(String[] args) throws Exception{
        //String url = "http://192.168.1.133:8096/comm/data/logo/url/v1/create";
        String url = "http://192.168.1.133:8096/customer/auth/attachment/v1/create";
        //String url = "http://192.168.1.32:18080/services/comm/data/logo/url/create";
        String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("User-Agent", USER_AGENT);
        post.setHeader("Content-Type", "application/json;charset=utf-8");

        Map<String,String> paramMap = new HashMap<String,String>();
//        paramMap.put("refId","1447741785535");
//        paramMap.put("uploadType","1");// 0-不带图存储  1-带图存储
//        paramMap.put("logoType","10");//1-作者，2-译者，3-来源,4-会员头像,5-tags_logo,6-会议,7-学校logo,8-组织背景图,9-组织主页头图,10-会员头像,11-简历头像
//        paramMap.put("logoSpec","0");//logo规格(0-头像原图、1-头像个人主页、2-头像动态、3-头像推荐-文章-视频终端页、4-头像回复、5-会议logo、6-会议大图)
//        paramMap.put("visitSiteId","");
//        paramMap.put("logoUrl","");
//        paramMap.put("extName","JPG");
//        paramMap.put("fileContent", ImageTestUtil.GetImageStr("D:/tmp/img01.jpg"));

        paramMap.put("customerId","1468304474671");
        paramMap.put("attType","8");
        paramMap.put("attPositionType","2");
        paramMap.put("attCode","123456789");
        paramMap.put("attPath","http://img05.allinmd.cn/public1/M01/0D/B9/wKgBL1oeeNqAOItBAAHHDtXpUgM476.jpg");
        paramMap.put("visitSiteId","1");
        paramMap.put("extName","jpg");
        paramMap.put("fileContent", ImageTestUtil.GetImageStr("D:/tmp/img/test.jpg"));
        String jsonString = new GsonBuilder().create().toJson(paramMap).toString();

        StringEntity entity = new StringEntity(jsonString);
        post.setEntity(entity);

        //List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
//        urlParameters.add(new BasicNameValuePair("refId", "1447741785535"));
//        urlParameters.add(new BasicNameValuePair("uploadType", "1"));
//        urlParameters.add(new BasicNameValuePair("logoType", "10"));
//        urlParameters.add(new BasicNameValuePair("logoSpec", "0"));
//        urlParameters.add(new BasicNameValuePair("extName", "JPG"));
//        urlParameters.add(new BasicNameValuePair("fileContent", ImageTestUtil.GetImageStr("D:/tmp/img01.jpg")));
        //urlParameters.add(new BasicNameValuePair("queryJson", jsonString));


        //post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        System.out.println("Response Code : "  + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader( new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result.toString());
    }
}
