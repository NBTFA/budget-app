package com.me.budgetbackend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.me.budgetbackend.entity.Notification;
import com.me.budgetbackend.entity.User;
import com.me.budgetbackend.exceptions.UserNotFoundException;
import com.me.budgetbackend.mapper.ContinuousRecordMapper;
import com.me.budgetbackend.mapper.UserMapper;
import com.me.budgetbackend.mapper.UserMapper_plus;
import com.me.budgetbackend.service.UserService;
import com.me.budgetbackend.utils.JwtUtils;
import com.me.budgetbackend.utils.Result;
import com.me.budgetbackend.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper_plus userMapperPlus;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user)
    {
        try {
            userService.login(user);
        } catch (UserNotFoundException e) {
            return Result.error(ResultCode.USER_NOT_FOUND);
        }
        String token = JwtUtils.generateToken(user.getUsername());
        return Result.ok().data("token", token);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user)
    {
        try {
            userService.register(user);
        } catch (UserNotFoundException e) {
            return Result.error(ResultCode.USER_ALREADY_EXISTS);
        }
        String token = JwtUtils.generateToken(user.getUsername());
        return Result.ok().data("token", token);
    }

    @GetMapping("/avatar")
    public Result getAvatar(@RequestHeader("Authorization") String token)
    {
        try{
            return Result.ok().data("avatar", userService.getAvatar(token));
        } catch (Exception e) {
            return Result.error(ResultCode.USER_NOT_FOUND);
        }
    }

    @GetMapping("/rankList")
    public Result getRankList(@RequestHeader("Authorization") String token)
    {
        try{
            return Result.ok().data("rankUsers", userService.getRankList(token));
        } catch (Exception e) {
            return Result.error(ResultCode.USER_NOT_FOUND);
        }
    }

    @GetMapping("/todoList")
    public Result getTodoList(@RequestHeader("Authorization") String token)
    {
        try{
            return Result.ok().data("todoList", userService.getTodoList(token));
        } catch (Exception e) {
            return Result.error(ResultCode.USER_NOT_FOUND);
        }
    }

    @GetMapping("/continue")
    public Result getContinue(@RequestHeader("Authorization") String token)
    {
        try{
            return Result.ok().data("continue", userService.getContinue(token));
        } catch (Exception e) {
            return Result.error(ResultCode.USER_NOT_FOUND);
        }
    }

    @GetMapping("/notification")
    public Result getNotification(@RequestHeader("Authorization") String token)
    {
        try{
            Result result = Result.ok();
            List<Notification> notifications = userService.getNotification(token);
            result.data("notifications", notifications);
            result.data("notificationNum", notifications.size());
            return result;
        } catch (Exception e) {
            return Result.error(ResultCode.USER_NOT_FOUND);
        }
    }

    //分页查询
    @GetMapping("/findByPage")
    public IPage findByPage()
    {
        //设置起始值页和每页数据数量
        Page<User> page = new Page<>(0, 10);
        IPage iPage = userMapperPlus.selectPage(page, null);
        return iPage;
    }

}
