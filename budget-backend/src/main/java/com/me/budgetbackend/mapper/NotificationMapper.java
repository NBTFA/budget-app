package com.me.budgetbackend.mapper;

import com.me.budgetbackend.entity.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationMapper {
    @Select("select * from notification where user_id = #{user_id} order by created_date desc")
    public List<Notification> selectNotificationByUserId(Long user_id);
}
