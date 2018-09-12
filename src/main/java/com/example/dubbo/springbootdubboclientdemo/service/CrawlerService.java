package com.example.dubbo.springbootdubboclientdemo.service;

import com.example.dubbo.springbootdubboclientdemo.form.QueryInfoForm;
import com.example.dubbo.springbootdubboclientdemo.po.IdIpPo;
import com.example.dubbo.springbootdubboclientdemo.po.InfoPo;

import java.io.IOException;
import java.util.List;

public interface CrawlerService {

    List<String> getProviderList() throws IOException;

    List<IdIpPo> getIpIdMap(String interfaceName) throws IOException;

    InfoPo getInfo(QueryInfoForm form) throws IOException;
}
