package com.example.dubbo.springbootdubboclientdemo.controller;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @RequestMapping(value = "/router", method = {RequestMethod.GET, RequestMethod.POST})
    public String router() {

        // 普通编码配置方式
        ApplicationConfig application = new ApplicationConfig();
        application.setName("bazinga-consumer");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");

        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setApplication(application);

        //reference.setRegistry(registry);
        reference.setUrl("dubbo://192.168.119.1:20880/com.example.dubbo.springbootdubboserverdemo.service.HelloService");

        reference.setInterface("com.example.dubbo.springbootdubboserverdemo.service.HelloService");
        reference.setGeneric(true); // 声明为泛化接口

        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(reference);

        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("name", "lili");
        mapParams.put("time", "2018-11-11 12:00:00");
        mapParams.put("num", 1);

        // 基本类型以及Date,List,Map等不需要转换，直接调用
        Object result = genericService.$invoke("sayHello", new String[] { "com.example.dubbo.springbootdubboserverdemo.requestform.HelloRequestForm" },
                new Object[] { mapParams });

        return result.toString();
    }
}
