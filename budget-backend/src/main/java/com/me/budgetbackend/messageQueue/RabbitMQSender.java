package com.me.budgetbackend.messageQueue;

import com.me.budgetbackend.controller.ChatController;
import com.me.budgetbackend.entity.ChatMessage;
import com.me.budgetbackend.entity.DBInstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RabbitMQSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQSender.class);
    public void pushToDBQueue(DBInstructor<?> dbInstructor) throws IOException {
        logger.info("Push to DB Queue");
        rabbitTemplate.convertAndSend("exchange_direct", "database", dbInstructor.toByteArray());
    }

    public void pushToEmailQueue(String email) {
        logger.info("Push to Email Queue");
        rabbitTemplate.convertAndSend("exchange_direct", "email", email);
    }
}
