package com.me.budgetbackend.controller;

import com.me.budgetbackend.exceptions.UserNotFoundException;
import com.me.budgetbackend.service.EmailService;
import com.me.budgetbackend.utils.Result;
import com.me.budgetbackend.utils.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    @PostMapping("/sendCode")
    public Result sendCode(@RequestBody String email)
    {
        try {
            logger.info("sendCode: " + email);
            emailService.sendCode(email);
        } catch (UserNotFoundException e) {
            return Result.error(ResultCode.USER_NOT_FOUND);
        }
        return Result.ok();
    }
}
