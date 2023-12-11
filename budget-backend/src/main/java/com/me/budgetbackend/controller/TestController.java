package com.me.budgetbackend.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    //get方法.../test?name=11
    //可以用parameter获取
    @ApiOperation("测试")
    @RequestMapping("/test")
    public String hello(String name)
    {
        return "name: "+name;
    }
}
