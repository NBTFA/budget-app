package com.me.budgetbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/budget")
public class BudgetController {
    @GetMapping("progress")
    public Integer getProgress()
    {
        return 0;
    }
}
