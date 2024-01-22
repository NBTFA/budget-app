package com.me.budgetbackend.mapper;

import com.me.budgetbackend.entity.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationMapper {
    @Result(column = "user_id", property = "user_id")
    @Result(column = "message", property = "message")
    @Result(column = "is_read", property = "is_read")
    @Result(column = "created_at", property = "created_at")
    @Result(column = "from_user_name", property = "from_user_name")
    @Select("select * from notifications where user_id = #{user_id} order by created_at desc")
    public List<Notification> selectNotificationByUserId(Long user_id);

    @Result(column = "user_id", property = "user_id")
    @Result(column = "message", property = "message")
    @Result(column = "is_read", property = "is_read")
    @Result(column = "created_at", property = "created_at")
    @Result(column = "from_user_name", property = "from_user_name")
    @Select("insert into notifications(user_id, message, is_read, created_at, from_user_name) values(#{user_id}, #{message}, #{is_read}, #{created_at}, #{from_user_name})")
    public void insert(Notification notification);
}
