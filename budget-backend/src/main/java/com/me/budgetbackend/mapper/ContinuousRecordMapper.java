package com.me.budgetbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ContinuousRecordMapper {
    @Select("select user_id from continuous_record order by count desc limit 5")
    public Integer[] selectTopFiveUserId();
}
