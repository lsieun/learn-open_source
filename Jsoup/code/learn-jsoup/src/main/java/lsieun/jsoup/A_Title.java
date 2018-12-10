package lsieun.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class A_Title {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("http://www.baidu.com/").get();
        String title = doc.title();
        System.out.println(title);
    }
}
