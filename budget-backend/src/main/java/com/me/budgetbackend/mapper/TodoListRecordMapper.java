package com.me.budgetbackend.mapper;

import com.me.budgetbackend.entity.TodoListRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TodoListRecordMapper {
    @Select("select * from TodoListRecords where user_id = #{user_id}")
    public List<TodoListRecord> selectByUserId(Long user_id);

    @Select("insert into TodoListRecords(user_id, created_date, completed_date, completed, description) values(#{user_id}, #{created_date}, #{completed_date}, #{completed}, #{description})")
    public int insert(TodoListRecord todoListRecord);
}
