package com.me.budgetbackend.controller;

import com.me.budgetbackend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/budget")
public class BudgetController {
    @Autowired
    private UserMapper userMapper;
    @GetMapping("progress")
    public Integer getProgress()
    {
        return 0;
    }
}
