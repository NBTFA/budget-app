package com.me.budgetbackend.mapper;

import com.me.budgetbackend.entity.ChatMessageDAO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMessageMapper {
    @Insert("insert into ChatMessages(user_id, content, time, group_id) values(#{user_id}, #{content}, #{time}, #{group_id})")
    public void insert(ChatMessageDAO chatMessage);
}
