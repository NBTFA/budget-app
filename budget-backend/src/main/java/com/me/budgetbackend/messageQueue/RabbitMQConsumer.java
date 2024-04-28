package com.me.budgetbackend.messageQueue;

import cn.hutool.core.util.RandomUtil;
import com.me.budgetbackend.controller.ChatController;
import com.me.budgetbackend.entity.ChatMessage;
import com.me.budgetbackend.entity.ChatMessageDAO;
import com.me.budgetbackend.entity.DBInstructor;
import com.me.budgetbackend.entity.Notification;
import com.me.budgetbackend.mapper.ChatMessageMapper;
import com.me.budgetbackend.mapper.NotificationMapper;
import com.me.budgetbackend.utils.ChatMessageUtils;
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
    private NotificationMapper notificationMapper;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private RedisTemplate redisTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConsumer.class);
    @RabbitListener(queues = "queue_db")
    public void processDBMessage(byte[] dbInstructor) {
        DBInstructor<?> dbInstructor1 = SerializationUtils.deserialize(dbInstructor);
        String dbName = dbInstructor1.getDbName();
        if (dbName.equals("chatMessages")) {
            processChatMessage(dbInstructor1);
        }
        else if (dbName.equals("Notifications")) {
            processNotificationMessage(dbInstructor1);
        }
    }

    private void processChatMessage(DBInstructor<?> dbInstructor) {
        String operation = dbInstructor.getOperation();
        ChatMessageDAO chatMessageDAO = ChatMessageUtils.convertToChatMessageDAO((ChatMessage) dbInstructor.getContent());
        if (operation.equals("insert")) {
            chatMessageMapper.insert(chatMessageDAO);
        }
    }

    private void processNotificationMessage(DBInstructor<?> dbInstructor) {
        String operation = dbInstructor.getOperation();
        Notification notification = (Notification) dbInstructor.getContent();
        logger.info(notification.getMessage());
        if (operation.equals("insert")) {
            notificationMapper.insert(notification);
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
