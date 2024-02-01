package com.me.budgetbackend.controller;

import com.me.budgetbackend.entity.Administrator;
import com.me.budgetbackend.entity.User;
import com.me.budgetbackend.exceptions.AdminAlreadyExistException;
import com.me.budgetbackend.exceptions.AdminNotFoundException;
import com.me.budgetbackend.exceptions.UserNotFoundException;
import com.me.budgetbackend.service.AdministratorService;
import com.me.budgetbackend.utils.JwtUtils;
import com.me.budgetbackend.utils.Result;
import com.me.budgetbackend.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
