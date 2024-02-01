package com.me.budgetbackend.mapper;

import com.me.budgetbackend.entity.Administrator;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdministratorMapper {
    @Select("select * from administrators where username = #{username} and password = #{password}")
    public Administrator login(Administrator admin);

    @Select("select * from administrators where username = #{username}")
    public Administrator selectByUsername(String username);

    @Insert("insert into administrators (username, password, email, avatar, created_at) VALUES (#{username}, #{password}, #{email}, #{avatar}, #{created_at})")
    public void insert(Administrator admin);
}
