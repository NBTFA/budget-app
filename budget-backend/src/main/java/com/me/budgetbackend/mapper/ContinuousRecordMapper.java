package com.me.budgetbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ContinuousRecordMapper {
    @Select("select user_id from continuous_record order by count desc limit 5")
    public Integer[] selectTopFiveUserId();

    @Select("select count from continuous_record where user_id = #{user_id}")
    public Integer selectCountByUserId(Long user_id);
}
