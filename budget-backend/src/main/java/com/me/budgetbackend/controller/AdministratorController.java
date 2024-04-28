package com.me.budgetbackend.controller;

import com.me.budgetbackend.entity.*;
import com.me.budgetbackend.exceptions.AdminAlreadyExistException;
import com.me.budgetbackend.exceptions.AdminNotFoundException;
import com.me.budgetbackend.exceptions.UserNotFoundException;
import com.me.budgetbackend.messageQueue.RabbitMQConsumer;
import com.me.budgetbackend.service.AdministratorService;
import com.me.budgetbackend.utils.JwtUtils;
import com.me.budgetbackend.utils.Result;
import com.me.budgetbackend.utils.ResultCode;
import com.me.budgetbackend.utils.URLDecoderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdministratorController {
    @Autowired
    private AdministratorService administratorService;
    private static final Logger logger = LoggerFactory.getLogger(AdministratorController.class);

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

    @GetMapping("/user")
    public Result getUser(@RequestHeader("Authorization") String token)
    {
        try {
            List<User> users = administratorService.getUser(token);
            Result result = Result.ok();
            result.data("users", users);
            return result;
        } catch (AdminNotFoundException e) {
            return Result.error(ResultCode.ADMIN_NOT_FOUND);
        }
    }

    @DeleteMapping("/user")
    public Result deleteUser(@RequestHeader("Authorization") String token, @RequestBody User user)
    {
        try {
            administratorService.deleteUser(token, user);
            return Result.ok();
        } catch (AdminNotFoundException e) {
            return Result.error(ResultCode.ADMIN_NOT_FOUND);
        }
    }

    @PostMapping("/user")
    public Result updateUser(@RequestHeader("Authorization") String token, @RequestBody User user)
    {
        try {
            administratorService.updateUser(token, user);
            return Result.ok();
        } catch (AdminNotFoundException e) {
            return Result.error(ResultCode.ADMIN_NOT_FOUND);
        }
    }

    @GetMapping("/adminRequest")
    public Result processAdminRequest(@RequestHeader("Authorization") String token)
    {
        try {
            Result result = Result.ok();
            result.data("admins", administratorService.getAdmins(token));
            result.data("isRoot", administratorService.getCurrentAdminAuthority(token));
            return result;
        } catch (AdminNotFoundException e) {
            return Result.error(ResultCode.ADMIN_NOT_FOUND);
        }
    }

    @DeleteMapping("/admin")
    public Result deleteAdmin(@RequestHeader("Authorization") String token, @RequestBody Administrator admin)
    {
        try {
            administratorService.deleteAdmin(token, admin);
            return Result.ok();
        } catch (AdminNotFoundException e) {
            return Result.error(ResultCode.ADMIN_NOT_FOUND);
        }
    }

    @PostMapping("/admin")
    public Result updateAdmin(@RequestHeader("Authorization") String token, @RequestBody Administrator admin)
    {
        try {
            administratorService.updateAdmin(token, admin);
            return Result.ok();
        } catch (AdminNotFoundException e) {
            return Result.error(ResultCode.ADMIN_NOT_FOUND);
        }
    }

    @PostMapping("/sendToUser")
    public Result sendToUser(@RequestHeader("Authorization") String token, @RequestBody SendToUserRequest request)
    {
        try {
            administratorService.sendToUser(token, request);
            return Result.ok();
        } catch (IOException | UserNotFoundException | AdminNotFoundException e) {
            return Result.error(ResultCode.ADMIN_NOT_FOUND);
        } 
    }

    @PostMapping("/sendToAll")
    public Result sendToAll(@RequestHeader("Authorization") String token, @RequestBody String message)
    {
        try {
            message = URLDecoderUtils.decode(message);
            logger.info("Send to all: " + message);
            administratorService.sendToAll(token, message.substring(0, message.length()-1));
            return Result.ok();
        } catch (AdminNotFoundException | IOException e) {
            return Result.error(ResultCode.ADMIN_NOT_FOUND);
        }
    }

    @DeleteMapping("/notification")
    public Result deleteNotification(@RequestHeader("Authorization") String token, @RequestBody Notification notification)
    {
        try {
            administratorService.deleteNotification(token, notification);
            return Result.ok();
        } catch (AdminNotFoundException e) {
            return Result.error(ResultCode.ADMIN_NOT_FOUND);
        }
    }

    @GetMapping("/notification")
    public Result getNotification(@RequestHeader("Authorization") String token)
    {
        try {
            Result result = Result.ok();
            List<Notification> notifications = administratorService.getNotification(token);
            List<User> users = administratorService.getUser(token);
            result.data("notifications", notifications);
            result.data("users", users);
            return result;
        } catch (AdminNotFoundException e) {
            return Result.error(ResultCode.ADMIN_NOT_FOUND);
        }
    }

    @GetMapping("/categoryRequest")
    public Result getCategory(@RequestHeader("Authorization") String token)
    {
        try {
            Result result = Result.ok();
            List<Category> categories = administratorService.getCategory(token);
            result.data("categories", categories);
            return result;
        } catch (AdminNotFoundException e) {
            return Result.error(ResultCode.ADMIN_NOT_FOUND);
        }
    }

    @GetMapping("/continuousRequest")
    public Result getContinuous(@RequestHeader("Authorization") String token)
    {
        try {
            Result result = Result.ok();
            List<ContinuousRecord> continuousStats = administratorService.getContinuous(token);
            result.data("continuousRecords", continuousStats);
            return result;
        } catch (AdminNotFoundException e) {
            return Result.error(ResultCode.ADMIN_NOT_FOUND);
        }
    }

    @GetMapping("/budgetRequest")
    public Result getBudget(@RequestHeader("Authorization") String token)
    {
        try {
            Result result = Result.ok();
            List<BudgetRecord> budgets = administratorService.getBudget(token);
            result.data("budgets", budgets);
            return result;
        } catch (AdminNotFoundException e) {
            return Result.error(ResultCode.ADMIN_NOT_FOUND);
        }
    }

    @DeleteMapping("/budget")
    public Result deleteBudget(@RequestHeader("Authorization") String token, @RequestBody BudgetRecord budget)
    {
        try {
            administratorService.deleteBudget(token, budget);
            return Result.ok();
        } catch (AdminNotFoundException e) {
            return Result.error(ResultCode.ADMIN_NOT_FOUND);
        }
    }

    @GetMapping("/todoRequest")
    public Result getTodoList(@RequestHeader("Authorization") String token)
    {
        try {
            Result result = Result.ok();
            List<TodoListRecord> todos = administratorService.getTodoList(token);
            result.data("todoLists", todos);
            return result;
        } catch (AdminNotFoundException e) {
            return Result.error(ResultCode.ADMIN_NOT_FOUND);
        }
    }

    @DeleteMapping("/todo")
    public Result deleteTodoList(@RequestHeader("Authorization") String token, @RequestBody TodoListRecord todo)
    {
        try {
            administratorService.deleteTodoList(token, todo);
            return Result.ok();
        } catch (AdminNotFoundException e) {
            return Result.error(ResultCode.ADMIN_NOT_FOUND);
        }
    }
}
