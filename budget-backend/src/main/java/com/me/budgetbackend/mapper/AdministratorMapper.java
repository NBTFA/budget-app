package com.me.budgetbackend.mapper;

import com.me.budgetbackend.entity.Administrator;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdministratorMapper {
    @Select("select * from administrators where username = #{username} and password = #{password}")
    public Administrator login(Administrator admin);

    @Select("select * from administrators where username = #{username}")
    public Administrator selectByUsername(String username);

    @Insert("insert into administrators (username, password, email, avatar, created_at) VALUES (#{username}, #{password}, #{email}, #{avatar}, #{created_at})")
    public void insert(Administrator admin);

    @Select("select * from administrators")
    public List<Administrator> selectAll();

    @Delete("delete from administrators where username = #{username}")
    public void deleteByUsername(String username);

    @Delete("delete from administrators where id = #{id}")
    public void deleteById(Integer id);

    @Update("update administrators set username = #{username}, email = #{email}, root = #{root} where id = #{id}")
    public void updateAdmin(String username, String email, Boolean root, Integer id);

}
