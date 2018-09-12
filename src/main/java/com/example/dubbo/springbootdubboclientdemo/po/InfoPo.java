package com.example.dubbo.springbootdubboclientdemo.po;

import lombok.Data;

import java.util.List;

@Data
public class InfoPo {
    String charset;
    /**
     * 状态
     * */
    String status;

    String logger;
    /**
     * 应用名
     * */
    String applicationName;
    /**
     * 进程号
     * */
    String processCode;
    /**
     * 方法列表
     * */
    String methodList;
    /**
     * 动态配置
     * */
    String DynamicConfiguration;
    /**
     *  default.timeout
     * */
    String  defaultTimeout;
    /**
     * 服务地址
     * */
    String serverAddress;
    /**
     * 绑定所有IP
     * */
    String bindAllIp;
    /**
     * 时间戳
     * */
    String timpstrap;
    /**
     * default.owner
     * */
    String defaultOwner;
    /**
     * application.version
     * */
    String applicationVersion;
    /**
     * 检查
     * */
    String check;
    /**
     * 版本
     * */
    String version;
    /**
     * 所属端
     * */
    String belongTo;
    /**
     * 接口名
     * */
    String interfaceName;
    /**
     * revision
     * */
    String revision;
    /**
     * Dubbo版本
     * */
    String dubboVersion;
    /**
     * environment
     * */
    String environment;
    /**
     * default.retries
     * */
    String defaultRetries;
    /**
     * default.service.filter
     * */
    String defaultServiceFilter;
    /**
     * 主机名
     * */
    String hostName;
    /**
     * default.accepts
     * */
    String defaultAccepts;
    /**
     * 类型
     * */
    String type;
}
