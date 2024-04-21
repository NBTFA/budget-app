package com.me.budgetbackend.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ChatMessage {
    public String user;
    public String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "GMT")
    public Date time;
    public boolean isMine;

    @Override
    public String toString() {
        return "ChatMessage{" +
                "user='" + user + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", isMine=" + isMine +
                '}';
    }
}
