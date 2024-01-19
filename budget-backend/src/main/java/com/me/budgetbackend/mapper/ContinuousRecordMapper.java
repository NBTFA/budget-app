package com.me.budgetbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.me.budgetbackend.entity.ContinuousRecord;

@Mapper
public interface ContinuousRecordMapper {
    @Select("select user_id from ContinuousRecords order by count desc limit 5")
    public Integer[] selectTopFiveUserId();

    @Select("select count from ContinuousRecords where user_id = #{user_id}")
    public Integer selectCountByUserId(Long user_id);

    @Select("insert into ContinuousRecords(user_id, count, record_date) values(#{user_id}, #{count}, #{record_date})")
    public void insert(ContinuousRecord continuousRecord);
}
