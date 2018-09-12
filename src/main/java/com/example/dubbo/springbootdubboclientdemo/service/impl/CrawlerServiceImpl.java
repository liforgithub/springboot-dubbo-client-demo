package com.example.dubbo.springbootdubboclientdemo.service.impl;

import com.example.dubbo.springbootdubboclientdemo.form.QueryInfoForm;
import com.example.dubbo.springbootdubboclientdemo.po.IdIpPo;
import com.example.dubbo.springbootdubboclientdemo.po.InfoPo;
import com.example.dubbo.springbootdubboclientdemo.service.CrawlerService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CrawlerServiceImpl implements CrawlerService {

    private Connection.Response firstResponse;
    private final String FIRST_URL = "http://172.21.3.42:8080/governance/services";
    private Map<String, String> headers = new HashMap<>();

    private void initData() throws IOException {
        this.headers.put("Host", "172.21.3.42:8080");
        this.headers.put("Connection", "keep-alive");
        this.headers.put("Authorization", "Basic cm9vdDptYXN0ZXIxMjM=");
        this.headers.put("Upgrade-Insecure-Requests", "1");
        this.headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
        this.headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        this.headers.put("Accept-Language", "zh-CN,zh;q=0.9");

        this.firstResponse = Jsoup.connect(FIRST_URL)
                .headers(headers)
                .method(Connection.Method.GET)
                .execute();
    }

    @Override
    public List<String> getProviderList() throws IOException {
        if (this.headers.size() == 0) this.initData();

        Document doc = this.firstResponse.parse();
        Elements inputs = doc.select("input[name=ids]");

        List<String> providers = new ArrayList<>();
        for (Element input : inputs) {
            providers.add(input.val());
        }

        return providers;
    }

    @Override
    public List<IdIpPo> getIpIdMap(String interfaceName) throws IOException {
        if (this.headers.size() == 0) this.initData();

        String providerUrl = FIRST_URL + "/" + interfaceName + "/providers";
        Connection.Response res = Jsoup.connect(providerUrl)
                .headers(this.headers)
                .cookies(this.firstResponse.cookies())
                .method(Connection.Method.GET)
                .execute();

        List<IdIpPo> ipIdPos = new ArrayList<>();
        Document doc = res.parse();
        Elements inputs = doc.select("input[name=ids]");
        for (Element input : inputs) {
            String id = input.val();
            Elements as = doc.select("a[href=providers/" + id + "]");
            String ip = as.get(0).text();

            IdIpPo po = new IdIpPo();
            po.setId(id);
            po.setIp(ip);
            ipIdPos.add(po);
        }

        return ipIdPos;
    }

    @Override
    public InfoPo getInfo(QueryInfoForm form) throws IOException {
        if (this.headers.size() == 0) this.initData();

        String providerUrl = FIRST_URL + "/" + form.getInterfaceName() + "/providers/" + form.getId();
        Connection.Response res = Jsoup.connect(providerUrl)
                .headers(this.headers)
                .cookies(this.firstResponse.cookies())
                .method(Connection.Method.GET)
                .execute();

        Map<String, String> infoMap = new HashMap<>();
        Document doc = res.parse();
        Element table = doc.select("table[class=info]").first();
        Elements trs = table.select("tr");
        for (Element tr : trs) {
            String th = tr.select("th").first().text().trim();
            th = th.substring(0, th.length() - 1);
            String td = tr.select("td").first().text();
            infoMap.put(th, td);
        }

        InfoPo po = new InfoPo();
        for (Map.Entry<String, String> entry : infoMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            switch (key) {
                case "charset":
                    po.setCharset(value);
                    break;
                case "状态":
                    po.setStatus(value);
                    break;
                case "logger":
                    po.setLogger(value);
                    break;
                case "应用名":
                    po.setApplicationName(value);
                    break;
                case "进程号":
                    po.setProcessCode(value);
                    break;
                case "方法列表":
                    po.setMethodList(value);
                    break;
                case "动态配置":
                    po.setDynamicConfiguration(value);
                    break;
                case "default.timeout":
                    po.setDefaultTimeout(value);
                    break;
                case "服务地址":
                    po.setServerAddress(value);
                    break;
                case "绑定所有IP":
                    po.setBindAllIp(value);
                    break;
                case "时间戳":
                    po.setTimpstrap(value);
                    break;
                case "default.owner":
                    po.setDefaultOwner(value);
                    break;
                case "application.version":
                    po.setApplicationVersion(value);
                    break;
                case "检查":
                    po.setCheck(value);
                    break;
                case "版本":
                    po.setVersion(value);
                    break;
                case "所属端":
                    po.setBelongTo(value);
                    break;
                case "接口名":
                    po.setInterfaceName(value);
                    break;
                case "revision":
                    po.setRevision(value);
                    break;
                case "Dubbo版本":
                    po.setDubboVersion(value);
                    break;
                case "environment":
                    po.setEnvironment(value);
                    break;
                case "default.retries":
                    po.setDefaultRetries(value);
                    break;
                case "default.service.filter":
                    po.setDefaultServiceFilter(value);
                    break;
                case "主机名":
                    po.setHostName(value);
                    break;
                case "default.accepts":
                    po.setDefaultAccepts(value);
                    break;
                case "类型":
                    po.setType(value);
            }
        }

        return po;
    }
}
