package com.me.budgetbackend.mapper;

import com.me.budgetbackend.entity.ServerStat;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ServerStatMapper {
    @Result(column = "new_users", property = "new_users")
    @Result(column = "total_users", property = "total_users")
    @Result(column = "total_records", property = "total_records")
    @Select("select * from serverstats order by date desc limit 2")
    public List<ServerStat> selectLastTwoStats();

    @Result(column = "new_users", property = "new_users")
    @Result(column = "total_users", property = "total_users")
    @Result(column = "total_records", property = "total_records")
    @Select("select * from serverstats")
    public List<ServerStat> selectAllStats();

    @Result(column = "new_users", property = "new_users")
    @Result(column = "total_users", property = "total_users")
    @Result(column = "total_records", property = "total_records")
    @Insert("insert into serverstats(date, new_users, total_users, total_records) values(#{date}, #{new_users}, #{total_users}, #{total_records})")
    public void insert(ServerStat serverStat);
}
