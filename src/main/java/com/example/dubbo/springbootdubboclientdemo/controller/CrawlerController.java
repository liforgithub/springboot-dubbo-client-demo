package com.example.dubbo.springbootdubboclientdemo.controller;

import com.example.dubbo.springbootdubboclientdemo.form.QueryInfoForm;
import com.example.dubbo.springbootdubboclientdemo.form.QueryIpListForm;
import com.example.dubbo.springbootdubboclientdemo.po.IdIpPo;
import com.example.dubbo.springbootdubboclientdemo.po.InfoPo;
import com.example.dubbo.springbootdubboclientdemo.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
public class CrawlerController {

    @Autowired
    CrawlerService crawlerService;

    @RequestMapping(value = "/queryProviderList", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody List<String> queryProviderList() throws IOException {
        return crawlerService.getProviderList();
    }

    @RequestMapping(value = "/getIpList", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody List<IdIpPo> getIpList(@RequestBody@Valid QueryIpListForm form) throws IOException {
        return crawlerService.getIpIdMap(form.getInterfaceName());
    }

    @RequestMapping(value = "/queryInfoList", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody InfoPo queryInfoList(@RequestBody@Valid QueryInfoForm form) throws IOException {
        return crawlerService.getInfo(form);
    }
}
