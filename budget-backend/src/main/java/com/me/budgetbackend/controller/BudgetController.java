package com.me.budgetbackend.controller;

import com.me.budgetbackend.mapper.UserMapper;
import com.me.budgetbackend.service.BudgetService;
import com.me.budgetbackend.utils.Result;
import com.me.budgetbackend.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/budget")
public class BudgetController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BudgetService budgetService;
    @GetMapping("/progress")
    public Result getProgress(@RequestHeader("Authorization") String token)
    {
        try{
            return Result.ok().data("progress", budgetService.getProgress(token));
        } catch (Exception e) {
            return Result.error(ResultCode.USER_NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public Result getAllBudget(@RequestHeader("Authorization") String token)
    {
        try{
            return Result.ok().data("budgetList", budgetService.getAllBudget(token));
        } catch (Exception e) {
            return Result.error(ResultCode.USER_NOT_FOUND);
        }
    }

    @GetMapping("/pieChart")
    public Result getPieChart(@RequestHeader("Authorization") String token)
    {
        try{
            return Result.ok().data("pieChart", budgetService.getPieChart(token));
        } catch (Exception e) {
            return Result.error(ResultCode.USER_NOT_FOUND);
        }
    }
}
