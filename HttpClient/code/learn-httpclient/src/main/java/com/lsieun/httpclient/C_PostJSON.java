package com.lsieun.httpclient;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.GsonBuilder;

public class C_PostJSON {
    public static void main(String[] args) throws Exception {
        String url = "http://192.168.1.133:8096/comm/data/logo/url/v1/create";
        //String url = "https://192.168.1.131:8091/services/comm/data/logo/url/v1/create";

        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("refId","1447741785535");
        paramMap.put("uploadType","1");// 0-不带图存储  1-带图存储
        paramMap.put("logoType","10");//1-作者，2-译者，3-来源,4-会员头像,5-tags_logo,6-会议,7-学校logo,8-组织背景图,9-组织主页头图,10-会员头像,11-简历头像
        paramMap.put("logoSpec","0");//logo规格(0-头像原图、1-头像个人主页、2-头像动态、3-头像推荐-文章-视频终端页、4-头像回复、5-会议logo、6-会议大图)
        paramMap.put("visitSiteId","");
        paramMap.put("logoUrl","https://img05.allinmd.cn/public1/M00/10/03/wKgBMFpF6ouATItmAAH9daP_dg4934_c_p_300_300.JPG");
        paramMap.put("extName","JPG");
        paramMap.put("fileContent", "");

        postJson(url,paramMap);
    }

    public static void postJson(String url,Map<String,String> paramMap) throws Exception {
        String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("User-Agent", USER_AGENT);
        post.setHeader("Content-Type", "application/json;charset=utf-8");

        String jsonString = new GsonBuilder().create().toJson(paramMap).toString();

        StringEntity entity = new StringEntity(jsonString);
        post.setEntity(entity);


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
