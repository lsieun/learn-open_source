package com.lsieun.jsoup;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class B_ALink {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("http://www.baidu.com/").get();
        Element content = doc.getElementById("wrapper");
        Elements links = content.getElementsByTag("a");
        for (Element link : links) {
            String linkHref = link.attr("href");
            String linkText = link.text();
            System.out.println(linkText + "\t" + linkHref);
        }
    }
}
