package com.me.budgetbackend.mapper;

import com.me.budgetbackend.entity.TodoListRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TodoListRecordMapper {
    @Result(column = "created_Date", property = "created_Date")
    @Result(column = "user_id", property = "user_id")
    @Result(column = "completed_Date", property = "completed_Date")
    @Select("select * from TodoListRecords where user_id = #{user_id}")
    public List<TodoListRecord> selectByUserId(Long user_id);

    @Result(column = "created_Date", property = "created_Date")
    @Result(column = "user_id", property = "user_id")
    @Result(column = "completed_Date", property = "completed_Date")
    @Select("insert into TodoListRecords(user_id, created_Date, completed_Date, completed, description, title) values(#{user_id}, #{created_Date}, #{completed_Date}, #{completed}, #{description}, #{title})")
    public void insert(TodoListRecord todoListRecord);

    @Result(column = "created_Date", property = "created_Date")
    @Result(column = "user_id", property = "user_id")
    @Result(column = "completed_Date", property = "completed_Date")
    @Delete("delete from TodoListRecords where id = #{id}")
    public void deleteById(Long id);

    @Result(column = "created_Date", property = "created_Date")
    @Result(column = "user_id", property = "user_id")
    @Result(column = "completed_Date", property = "completed_Date")
    @Select("update TodoListRecords set completed_Date = #{completed_Date}, completed = #{completed} where id = #{id}")
    public void completeById(TodoListRecord todoListRecord);

    @Result(column = "created_Date", property = "created_Date")
    @Result(column = "completed_Date", property = "completed_Date")
    @Result(column = "user_id", property = "user_id")
    @Select("select * from TodoListRecords where id = #{id}")
    public TodoListRecord selectById(Long id);

    @Result(column = "created_Date", property = "created_Date")
    @Result(column = "completed_Date", property = "completed_Date")
    @Result(column = "user_id", property = "user_id")
    @Select("select * from TodoListRecords")
    public List<TodoListRecord> selectAll();
}
