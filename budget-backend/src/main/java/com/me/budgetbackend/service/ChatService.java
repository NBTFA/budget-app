package com.me.budgetbackend.service;

import com.me.budgetbackend.entity.ChatMessage;
import com.me.budgetbackend.entity.DBInstructor;
import com.me.budgetbackend.messageQueue.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ChatService {
    @Autowired
    private RabbitMQSender rabbitMQSender;

    public void pushToDBQueue(ChatMessage message) throws IOException {
        DBInstructor<ChatMessage> dbInstructor = new DBInstructor<>();
        dbInstructor.setDbName("chatMessages");
        dbInstructor.setOperation("insert");
        dbInstructor.setContent(message);
        rabbitMQSender.pushToDBQueue(dbInstructor);
    }
}
