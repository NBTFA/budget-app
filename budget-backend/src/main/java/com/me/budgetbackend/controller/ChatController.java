package com.me.budgetbackend.controller;

import com.me.budgetbackend.entity.ChatMessage;
import com.me.budgetbackend.entity.DBInstructor;
import com.me.budgetbackend.entity.Group;
import com.me.budgetbackend.messageQueue.RabbitMQSender;
import com.me.budgetbackend.utils.Result;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class ChatController {
    @Autowired
    private RabbitMQSender rabbitMQSender;
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/messages/{roomId}")
    public ChatMessage sendMessage(@DestinationVariable Integer roomId,
                                   ChatMessage message) throws IOException {
        // 打印message
        logger.info(message.toString());

        DBInstructor<ChatMessage> dbInstructor = new DBInstructor<>();
        dbInstructor.setDbName("chatMessages");
        dbInstructor.setOperation("insert");
        dbInstructor.setContent(message);
        rabbitMQSender.pushToDBQueue(dbInstructor);

        return message; // 将消息广播到订阅了"/topic/messages"的客户端
    }

    @GetMapping("/chat/groups")
    public Result getGroups(@RequestHeader("Authorization") String token)
    {
        return null;
    }
}
