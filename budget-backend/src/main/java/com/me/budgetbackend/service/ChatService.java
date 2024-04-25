package com.me.budgetbackend.service;

import com.me.budgetbackend.controller.ChatController;
import com.me.budgetbackend.entity.ChatMessage;
import com.me.budgetbackend.entity.ChatMessageDAO;
import com.me.budgetbackend.entity.DBInstructor;
import com.me.budgetbackend.mapper.ChatMessageMapper;
import com.me.budgetbackend.mapper.UserMapper;
import com.me.budgetbackend.messageQueue.RabbitMQSender;
import com.me.budgetbackend.utils.ChatMessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    private RabbitMQSender rabbitMQSender;
    @Autowired
    private ChatMessageMapper chatMessageMapper;
    @Autowired
    private UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    public void pushToDBQueue(ChatMessage message) throws IOException {
        DBInstructor<ChatMessage> dbInstructor = new DBInstructor<>();
        dbInstructor.setDbName("chatMessages");
        dbInstructor.setOperation("insert");
        dbInstructor.setContent(message);
        rabbitMQSender.pushToDBQueue(dbInstructor);
    }

    public List<ChatMessage> getMessages(Long group_id) {
        List<ChatMessageDAO> chatMessageDAOs = chatMessageMapper.selectByGroupId(group_id);
        logger.info(chatMessageDAOs.toString());
        List<ChatMessage> chatMessages = new ArrayList<>();
        for(ChatMessageDAO chatMessageDAO : chatMessageDAOs) {
            chatMessages.add(ChatMessageUtils.convertToChatMessage(chatMessageDAO, userMapper.selectUsernameById(chatMessageDAO.getUser_id())));
            logger.info(chatMessageDAO.toString());
        }
        return chatMessages;
    }
}
