package com.me.budgetbackend.messageQueue;

import cn.hutool.core.util.RandomUtil;
import com.me.budgetbackend.controller.ChatController;
import com.me.budgetbackend.entity.ChatMessage;
import com.me.budgetbackend.entity.ChatMessageDAO;
import com.me.budgetbackend.entity.DBInstructor;
import com.me.budgetbackend.mapper.ChatMessageMapper;
import com.me.budgetbackend.utils.SerializationUtils;
import com.me.budgetbackend.utils.URLDecoderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RabbitMQConsumer {
    @Autowired
    private ChatMessageMapper chatMessageMapper;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private RedisTemplate redisTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    @RabbitListener(queues = "queue_db")
    public void processDBMessage(byte[] dbInstructor) {
        DBInstructor<ChatMessage> dbInstructor1 = SerializationUtils.deserialize(dbInstructor);
        String operation = dbInstructor1.getOperation();
        ChatMessageDAO chatMessageDAO = new ChatMessageDAO(dbInstructor1.getContent());
        if (operation.equals("insert")) {
            chatMessageMapper.insert(chatMessageDAO);
        }
    }

    @RabbitListener(queues = "queue_email")
    public void processEmailMessage(String email) {
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
        logger.info("Email sent to: " + email);
    }
}
