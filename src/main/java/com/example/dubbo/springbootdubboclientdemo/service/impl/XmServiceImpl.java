package com.example.dubbo.springbootdubboclientdemo.service.impl;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class XmServiceImpl {

    public static void main(String[] args) throws IOException {

        String url = "https://m.jiazhengye.cn/article/detail?number=6717112845484570";
        Connection.Response firstResponse = Jsoup.connect(url).method(Connection.Method.GET).execute();

        Document doc = firstResponse.parse();
        Elements items = doc.select("div[class=item]");
        for (int i = 0; i < items.size(); i++) {
            Element item = items.get(i);
            switch (i) {
                case 0:
                    //标题
                    String title = item.select("div[class=title]").text().trim();
                    System.out.println(title);
                    break;
                case 1:
                    //分类
                    break;
                case 2:
                    //主题
                    Elements ps = item.select("div[class=content]");
                    System.out.println(ps.toString());
                    for (Element p : ps) {
                        Elements imgs = p.select("img");
                        for (Element img : imgs) {
                            String imgUrl = img.attr("src");
                            System.out.println(imgUrl);
                        }
                    }
                    break;
                case 3:
                    //版权
                    String banquan = item.select("div[class=content media clearfix l-h]").text();
                    System.out.println(banquan);
            }
        }
    }
}
