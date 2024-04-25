package com.me.budgetbackend.mapper;

import com.me.budgetbackend.entity.ChatMessageDAO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChatMessageMapper {
    @Insert("insert into ChatMessages(user_id, content, time, group_id) values(#{user_id}, #{content}, #{time}, #{group_id})")
    public void insert(ChatMessageDAO chatMessage);

    @Result(column = "user_id", property = "user_id")
    @Result(column = "content", property = "content")
    @Result(column = "time", property = "time")
    @Result(column = "group_id", property = "group_id")
    @Select("select * from ChatMessages where group_id = #{group_id}")
    public List<ChatMessageDAO> selectByGroupId(Long group_id);
}
