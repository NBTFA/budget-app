package com.me.budgetbackend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Setter
@Getter
public class ChatMessageDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Long user_id;
    public String content;
    public Date time;
    public int group_id;

    public ChatMessageDAO(ChatMessage chatMessage) {
        this.user_id = chatMessage.getUser_id();
        this.content = chatMessage.getContent();
        this.time = chatMessage.getTime();
        this.group_id = chatMessage.getGroupID();
    }
}
