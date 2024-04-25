package com.me.budgetbackend.service;

import cn.hutool.core.util.RandomUtil;
import com.me.budgetbackend.controller.ChatController;
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
    private JavaMailSender mailSender;
    @Autowired
    private RedisTemplate redisTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    public void sendCode(String email) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sapphirestudio@163.com");
        email = URLDecoderUtils.decode(email);
        email = email.substring(0, email.length()-1);
        message.setTo(email);
        message.setSubject("验证码 - 预算保镖");
        String code = RandomUtil.randomNumbers(6);
        String key = "email_code_"+email;
        redisTemplate.opsForValue().set(key, code, 300000, TimeUnit.MILLISECONDS);
        String text = "「验证码」您的验证码是: [" + code + "]，有效期5分钟\n\n\n"+"-----------------------\n\n";
        message.setText(text);
        mailSender.send(message);
    }
}
