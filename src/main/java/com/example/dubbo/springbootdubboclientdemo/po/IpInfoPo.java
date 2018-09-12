package com.example.dubbo.springbootdubboclientdemo.po;

import lombok.Data;

import java.util.Map;

@Data
public class IpInfoPo {
    String ip;
    Map<String, String> info;
}
