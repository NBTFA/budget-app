package com.me.budgetbackend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Table(name = "ChatMessages")
public class ChatMessageDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long user_id;
    @Column(name = "content")
    private String content;
    private String time;
    @Column(name = "group_id")
    private Long group_id;

    @Override
    public String toString() {
        return "ChatMessageDAO{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", group_id=" + group_id +
                '}';
    }
}
