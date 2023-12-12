package com.me.budgetbackend.mapper;

import com.me.budgetbackend.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user")
    public List<User> findAll();

    @Insert("insert into user values (#{id}, #{username}, #{password})")
    public int insert(User user);
}
