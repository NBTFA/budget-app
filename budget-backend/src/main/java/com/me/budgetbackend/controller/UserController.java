package com.me.budgetbackend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.me.budgetbackend.entity.User;
import com.me.budgetbackend.mapper.UserMapper;
import com.me.budgetbackend.mapper.UserMapper_plus;
import com.me.budgetbackend.service.UserService;
import com.me.budgetbackend.utils.JwtUtils;
import com.me.budgetbackend.utils.Result;
import com.me.budgetbackend.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserMapper_plus userMapperPlus;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user)
    {
        if(userService.Login(user))
        {
            String token = JwtUtils.generateToken(user.getUsername());
            return Result.ok().data("token", token);
        }
        else
            return Result.error();
    }

    @GetMapping("/user")
    public String query()
    {
        List<User> list = userMapper.findAll();
        System.out.println(list);
        return "查询用户";
    }

    //分页查询
    @GetMapping("/user/findByPage")
    public IPage findByPage()
    {
        //设置起始值页和每页数据数量
        Page<User> page = new Page<>(0, 10);
        IPage iPage = userMapperPlus.selectPage(page, null);
        return iPage;
    }

    @PostMapping("/user")
    public String save(User user)
    {
        int i = userMapper.insert(user);
        if(i>0)
        {
            return "success";
        }
        else
            return "fail";
    }
}
