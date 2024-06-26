package com.me.budgetbackend.mapper;

import com.me.budgetbackend.entity.User;
import org.apache.ibatis.annotations.*;

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

    @Update("update users set last_login = #{last_login} where username = #{username}")
    public void updateLastLoginByUsername(String last_login, String username);

    @Select("select * from users where username = #{username}")
    public User selectByUsername(String username);

    @Select("select total_budget from users where username = #{username}")
    public Integer selectTotalBudgetByUsername(String username);

    @Result(column = "created_at", property = "created_at")
    @Result(column = "total_budget", property = "total_budget")
    @Result(column = "used_budget", property = "used_budget")
    @Select("select * from users where id = #{id}")
    public User selectById(Integer id);

    //更新用户的total_budget值
    @Update("update users set total_budget = #{total_budget} where username = #{username}")
    public void updateTotalBudgetByUsername(Integer total_budget, String username);

    @Delete("delete from users where id = #{id}")
    public void deleteById(Integer id);

    @Update("update users set username = #{username}, email = #{email} where id = #{id}")
    public void updateUsernameAndEmailById(String username, String email, Integer id);

    @Select("select count(*) from users where date(created_at) = curdate()")
    public Integer selectTodayRegisterCount();

    @Select("select username from users where id = #{id}")
    public String selectUsernameById(Long id);
}
