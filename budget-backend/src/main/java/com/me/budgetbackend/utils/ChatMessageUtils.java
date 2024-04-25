package com.me.budgetbackend.utils;

import com.me.budgetbackend.entity.ChatMessage;
import com.me.budgetbackend.entity.ChatMessageDAO;

public class ChatMessageUtils {
    public static ChatMessageDAO convertToChatMessageDAO(ChatMessage chatMessage) {
        ChatMessageDAO chatMessageDAO = new ChatMessageDAO();
        chatMessageDAO.setUser_id(chatMessage.getUser_id());
        chatMessageDAO.setContent(chatMessage.getContent());
        chatMessageDAO.setTime(chatMessage.getTime());
        chatMessageDAO.setGroup_id(chatMessage.getGroup_id());
        return chatMessageDAO;
    }

    public static ChatMessage convertToChatMessage(ChatMessageDAO chatMessageDAO, String name) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUser_id(chatMessageDAO.getUser_id());
        chatMessage.setUser(name);
        chatMessage.setContent(chatMessageDAO.getContent());
        chatMessage.setTime(chatMessageDAO.getTime());
        chatMessage.setGroup_id(chatMessageDAO.getGroup_id());
        return chatMessage;
    }
}
