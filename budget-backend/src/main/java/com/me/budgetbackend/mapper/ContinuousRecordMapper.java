package com.me.budgetbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.me.budgetbackend.entity.ContinuousRecord;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ContinuousRecordMapper {
    @Select("select user_id from ContinuousRecords order by count desc limit 5")
    public Integer[] selectTopFiveUserId();

    @Select("select count from ContinuousRecords where user_id = #{user_id}")
    public Integer selectCountByUserId(Long user_id);

    @Select("insert into ContinuousRecords(user_id, count, record_date) values(#{user_id}, #{count}, #{record_date})")
    public void insert(ContinuousRecord continuousRecord);

    @Update("update ContinuousRecords set count = #{count} where user_id = #{user_id}")
    public void updateCountByUserId(Integer count, Long user_id);

    @Select("select record_date from ContinuousRecords where user_id = #{user_id}")
    public java.sql.Date selectRecordDateByUserId(Long user_id);

    @Update("update ContinuousRecords set record_date = #{record_date} where user_id = #{user_id}")
    public void updateRecordDateByUserId(java.sql.Date record_date, Long user_id);
}
