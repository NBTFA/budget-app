package com.me.budgetbackend.mapper;

import com.me.budgetbackend.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from users")
    public List<User> findAll();

    @Insert("INSERT INTO users (username, password, email, avatar, created_at) VALUES (#{username}, #{password}, #{email}, #{avatar}, #{created_at})")
    public int insert(User user);

    @Select("select * from users where username = #{username} and password = #{password}")
    public User login(User user);

    @Select("select * from users where username = #{username}")
    public User selectByUsername(String username);

    @Select("select * from users where id = #{id}")
    public User selectById(Integer id);
}
