package com.example.dubbo.springbootdubboclientdemo.controller;

import com.example.dubbo.springbootdubboclientdemo.form.QueryIpListForm;
import com.example.dubbo.springbootdubboclientdemo.po.IpInfoPo;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.net.URLDecoder.decode;

@RestController
@RequestMapping(value = "/zk")
public class ZkClientBaseController {

    @Value("${qianmi.dubbo.registry.address}")
    String dubboRegistryAddress;

    @GetMapping(value = "/queryProviderlist")
    public @ResponseBody List<String> queryProviderlist() {
        ZkClient zc = new ZkClient(dubboRegistryAddress, 5000, 5000, new SerializableSerializer());
        List<String> result = zc.getChildren("/dubbo");
        zc.close();
        return result;
    }

    @PostMapping(value = "/queryIpInfoList")
    public @ResponseBody List<IpInfoPo> queryIpInfoList(@RequestBody QueryIpListForm form) {
        ZkClient zc = new ZkClient(dubboRegistryAddress, 5000, 5000, new SerializableSerializer());
        List<String> ipInfoList = zc.getChildren("/dubbo/" + form.getInterfaceName() + "/providers");
        zc.close();

        return ipInfoList
                .stream()
                .map(ipInfo -> {
                    ipInfo = decode(ipInfo).replace("dubbo://", "http://");
                    IpInfoPo po = new IpInfoPo();
                    try {
                        po.setIp(new URL(ipInfo).getAuthority());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    po.setInfo(parseUrl(ipInfo));

                    return po;
                }).collect(Collectors.toList());
    }

    private Map<String, String> parseUrl(String URL) {
        Map<String, String> mapRequest = new HashMap<>();
        String strUrlParam = this.truncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }

        String[] arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual;
            arrSplitEqual = strSplit.split("[=]");

            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            } else {
                if (!arrSplitEqual[0].equals("")) {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    private String truncateUrlPage(String strURL) {
        String strAllParam = null;

        strURL = strURL.trim();
        String[] arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }

        return strAllParam;
    }
}
