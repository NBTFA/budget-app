package com.me.budgetbackend.service;

import cn.hutool.core.util.RandomUtil;
import com.me.budgetbackend.controller.ChatController;
import com.me.budgetbackend.messageQueue.RabbitMQSender;
import com.me.budgetbackend.utils.URLDecoderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class EmailService {
    @Autowired
    private RabbitMQSender rabbitMQSender;
    public void sendCode(String email) {
        rabbitMQSender.pushToEmailQueue(email);
    }
}
