package com.example.dubbo.springbootdubboclientdemo.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class QueryInfoForm {

    @NotEmpty(message = "接口名不能为空")
    private String interfaceName;
    @NotEmpty(message = "id不能为空")
    private String id;
}
