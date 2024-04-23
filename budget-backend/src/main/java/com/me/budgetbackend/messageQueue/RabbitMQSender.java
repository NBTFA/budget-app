package com.me.budgetbackend.messageQueue;

import com.me.budgetbackend.entity.ChatMessage;
import com.me.budgetbackend.entity.DBInstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RabbitMQSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void pushToDBQueue(DBInstructor<ChatMessage> dbInstructor) throws IOException {
        rabbitTemplate.convertAndSend("DB_exchange_direct", "database", dbInstructor.toByteArray());
    }
}
