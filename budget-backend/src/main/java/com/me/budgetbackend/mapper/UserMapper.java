package com.me.budgetbackend.mapper;

import com.me.budgetbackend.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Result(column = "created_at", property = "created_at")
    @Result(column = "total_budget", property = "total_budget")
    @Result(column = "used_budget", property = "used_budget")
    @Select("select * from users")
    public List<User> findAll();

    @Insert("INSERT INTO users (username, password, email, avatar, created_at, total_budget, used_budget) VALUES (#{username}, #{password}, #{email}, #{avatar}, #{created_at}, #{total_budget}, #{used_budget})")
    public int insert(User user);

    @Select("select * from users where username = #{username} and password = #{password}")
    public User login(User user);

    @Select("select * from users where username = #{username}")
    public User selectByUsername(String username);

    @Select("select total_budget from users where username = #{username}")
    public Integer selectTotalBudgetByUsername(String username);

    @Result(column = "created_at", property = "created_at")
    @Result(column = "total_budget", property = "total_budget")
    @Result(column = "used_budget", property = "used_budget")
    @Select("select * from users where id = #{id}")
    public User selectById(Integer id);
}
