package com.me.budgetbackend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.me.budgetbackend.entity.*;
import com.me.budgetbackend.exceptions.UserAlreadyExistException;
import com.me.budgetbackend.exceptions.UserNotFoundException;
import com.me.budgetbackend.mapper.ContinuousRecordMapper;
import com.me.budgetbackend.mapper.UserMapper;
import com.me.budgetbackend.mapper.UserMapper_plus;
import com.me.budgetbackend.service.UserService;
import com.me.budgetbackend.utils.JwtUtils;
import com.me.budgetbackend.utils.Result;
import com.me.budgetbackend.utils.ResultCode;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
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
        } catch (UserAlreadyExistException e) {
            return Result.error(ResultCode.USER_ALREADY_EXISTS);
        }
        String token = JwtUtils.generateToken(user.getUsername());
        return Result.ok().data("token", token);
    }

    @PostMapping("/initialize")
    public Result setBudgetInfo(@RequestHeader("Authorization") String token,
                                @RequestBody InitializeBudgetRequest initializeBudgetRequest)
    {
        try {
            userService.setBudgetInfo(token, initializeBudgetRequest.getTotalBudget(),
                                    initializeBudgetRequest.getBudgetCategories());
            return Result.ok();
        } catch (Exception e) {
            return Result.error(ResultCode.SET_BUDGET_INFO_FAILED);
        }
    }

    @GetMapping("/homeRequest")
    public Result processHomeRequest(@RequestHeader("Authorization") String token)
    {
        Result result = Result.ok();
        try {
            String url = userService.getAvatar(token);
            result = Result.ok();
            url = url == null ? "" : url;
            result.data("avatar", url);
        } catch (Exception e) {
            return Result.error(ResultCode.GET_AVATAR_FAILED);
        }

        try {
            List<Notification> notifications = userService.getNotification(token);
            result.data("notifications", notifications);
            result.data("notificationNum", notifications.size());
        } catch (Exception e) {
            return Result.error(ResultCode.GET_NOTIFICATION_FAILED);
        }

        try {
            int continueDays = userService.getContinue(token);
            result.data("continueNum", continueDays);
        } catch (Exception e) {
            return Result.error(ResultCode.GET_CONTINUE_FAILED);
        }

        try {
            List<RankUser> rankUsers = userService.getRankList(token);
            result.data("rankUsers", rankUsers);
        } catch (Exception e) {
            return Result.error(ResultCode.GET_RANK_USERS_FAILED);
        }

        try {
            List<TodoListRecord> todoList = userService.getTodoList(token);
            result.data("todoList", todoList);
        } catch (Exception e) {
            return Result.error(ResultCode.GET_TODO_LIST_FAILED);
        }

        try {
            int totalBudget = userService.getTotalBudget(token);
            result.data("totalBudget", totalBudget);
        } catch (Exception e) {
            return Result.error(ResultCode.GET_TOTAL_BUDGET_FAILED);
        }

        try {
            List<PieChartData> pieChartData = userService.getPieChartData(token);
            result.data("pieChartData", pieChartData);
        } catch (Exception e) {
            return Result.error(ResultCode.GET_PIE_CHART_DATA_FAILED);
        }

        try {
            List<BudgetRecord> budgetList = userService.getBudgetList(token);
            result.data("budgetList", budgetList);
        } catch (Exception e) {
            return Result.error(ResultCode.GET_BUDGET_LIST_FAILED);
        }

        int progress = userService.getProgress(token);
        result.data("progress", progress);

        String username = userService.getUserName(token);
        result.data("username", username);

        Long user_id = userService.getUserID(token);
        result.data("user_id", user_id);

        return result;
    }

    @GetMapping("/historyRequest")
    public Result processTodoRequest(@RequestHeader("Authorization") String token)
    {
        try{
            Result result = Result.ok();
            List<BudgetRecord> budgetList = userService.getBudgetList(token);
            result.data("budgetList", budgetList);

            String url = userService.getAvatar(token);
            url = url == null ? "" : url;
            result.data("avatar", url);

            List<Notification> notifications = userService.getNotification(token);
            result.data("notifications", notifications);
            result.data("notificationNum", notifications.size());

            int continueDays = userService.getContinue(token);
            result.data("continueNum", continueDays);

            return result;
        } catch (Exception e) {
            return Result.error(ResultCode.USER_NOT_FOUND);
        }
    }

    @GetMapping("/todoRequest")
    public Result processHistoryRequest(@RequestHeader("Authorization") String token)
    {
        try{
            Result result = Result.ok();
            List<TodoListRecord> todoList = userService.getTodoList(token);
            result.data("todoList", todoList);

            String url = userService.getAvatar(token);
            url = url == null ? "" : url;
            result.data("avatar", url);

            List<Notification> notifications = userService.getNotification(token);
            result.data("notifications", notifications);
            result.data("notificationNum", notifications.size());

            int continueDays = userService.getContinue(token);
            result.data("continueNum", continueDays);

            return result;
        } catch (Exception e) {
            return Result.error(ResultCode.USER_NOT_FOUND);
        }
    }

    @PostMapping("/todo")
    public Result addTodo(@RequestBody TodoListRecord todoListRecord, @RequestHeader("Authorization") String token)
    {
        try{
            userService.addTodo(todoListRecord, token);
            return Result.ok();
        } catch (Exception e) {
            return Result.error(ResultCode.ADD_TODO_FAILED);
        }
    }

    @DeleteMapping("/todo")
    public Result deleteTodo(@RequestBody TodoListRecord todoListRecord, @RequestHeader("Authorization") String token)
    {
        try{
            userService.deleteTodoById(todoListRecord.getId(), token);
            return Result.ok();
        } catch (Exception e) {
            return Result.error(ResultCode.DELETE_TODO_LIST_FAILED);
        }
    }

    @PostMapping("/todo/complete")
    public Result completeTodo(@RequestBody TodoListRecord todoListRecord, @RequestHeader("Authorization") String token)
    {
        try{
            userService.completeTodoById(todoListRecord.getId(), token);
            Date date = new Date(System.currentTimeMillis());
            Result result = Result.ok();
            result.data("completed_Date", date);
            return result;
        } catch (Exception e) {
            return Result.error(ResultCode.Set_TODO_LIST_FAILED);
        }
    }

    @PostMapping("/budget")
    public Result addBudget(@RequestBody BudgetRecord budgetRecord, @RequestHeader("Authorization") String token)
    {
            BudgetRecord bR = userService.addBudget(budgetRecord, token);
            Result result = Result.ok();
            result.data("budgetRecord", bR);
            return result;
    }

    @DeleteMapping("/budget")
    public Result deleteBudget(@RequestBody BudgetRecord budgetRecord, @RequestHeader("Authorization") String token)
    {
        try{
            userService.deleteBudgetById(budgetRecord.getId(), token);
            return Result.ok();
        } catch (Exception e) {
            return Result.error(ResultCode.DELETE_TODO_LIST_FAILED);
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
