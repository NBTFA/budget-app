package com.me.budgetbackend.mapper;

import com.me.budgetbackend.entity.BudgetRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BudgetRecordMapper {
    @Select("select * from BudgetRecords where user_id = #{user_id} order by created_date desc")
    public List<BudgetRecord> selectBudgetRecordByUserId(Long user_id);

    //得到一个用户id下所有不同的预算category
    @Select("select distinct category from BudgetRecords where user_id = #{user_id}")
    public List<String> selectCategoryByUserId(Long user_id);

    //得到一个用户id下某个category的预算总和
    @Select("select sum(amount) from BudgetRecords where user_id = #{user_id} and category = #{category}")
    public Integer selectSumByCategory(Long user_id, String category);
}
