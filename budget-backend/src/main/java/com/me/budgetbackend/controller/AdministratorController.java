package com.me.budgetbackend.controller;

import com.me.budgetbackend.entity.*;
import com.me.budgetbackend.exceptions.AdminAlreadyExistException;
import com.me.budgetbackend.exceptions.AdminNotFoundException;
import com.me.budgetbackend.exceptions.UserNotFoundException;
import com.me.budgetbackend.service.AdministratorService;
import com.me.budgetbackend.utils.JwtUtils;
import com.me.budgetbackend.utils.Result;
import com.me.budgetbackend.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdministratorController {
    @Autowired
    private AdministratorService administratorService;

    @PostMapping("/login")
    public Result login(@RequestBody Administrator admin)
    {
        try {
            administratorService.login(admin);
        } catch (AdminNotFoundException e) {
            return Result.error(ResultCode.ADMIN_NOT_FOUND);
        }
        String token = JwtUtils.generateToken(admin.getUsername());
        return Result.ok().data("token", token);
    }

    @PostMapping("/register")
    public Result register(@RequestBody Administrator admin)
    {
        try {
            administratorService.register(admin);
        } catch (AdminAlreadyExistException e) {
            return Result.error(ResultCode.ADMIN_ALREADY_EXISTS);
        }
        String token = JwtUtils.generateToken(admin.getUsername());
        return Result.ok().data("token", token);
    }

    @GetMapping("/serverStat")
    public Result newUsers(@RequestHeader("Authorization") String token)
    {
        try {
            List<NewUserStat> new_users = administratorService.newUsers(token);
            List<TotalUserStat> totalUsers = administratorService.totalUsers(token);
            List<TotalRecordStat> totalRecords = administratorService.totalRecords(token);
            Result result = Result.ok();
            result.data("newUsers", new_users);
            result.data("totalUsers", totalUsers);
            result.data("totalRecords", totalRecords);
            return result;
        } catch (AdminNotFoundException e) {
            return Result.error(ResultCode.ADMIN_NOT_FOUND);
        }
    }
    
}
