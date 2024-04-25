package com.me.budgetbackend.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class ChatMessage implements Serializable {
    public Long user_id;
    public String user;
    public String content;
    public String time;
    public boolean isMine;
    public Long group_id;


    @Override
    public String toString() {
        return "ChatMessage{" +
                "user='" + user_id + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", isMine=" + isMine +
                '}';
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(this);  // 写入当前对象
            oos.flush();
            return bos.toByteArray();
        } finally {
            if (oos != null) {
                oos.close();
            }
            bos.close();
        }
    }
}
