package com.me.budgetbackend.mapper;

import com.me.budgetbackend.entity.BudgetRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BudgetRecordMapper {
    @Result(column = "record_date", property = "record_date")
    @Result(column = "user_id", property = "user_id")
    @Select("select * from BudgetRecords where user_id = #{user_id} order by record_date desc")
    public List<BudgetRecord> selectBudgetRecordByUserId(Long user_id);

    @Result(column = "record_date", property = "record_date")
    @Result(column = "user_id", property = "user_id")
    @Select("select * from BudgetRecords")
    public List<BudgetRecord> selectAll();

    //返回最新添加的一条数据
    @Result(column = "record_date", property = "record_date")
    @Result(column = "user_id", property = "user_id")
    @Select("select * from BudgetRecords where user_id = #{user_id} order by record_date desc limit 1")
    public BudgetRecord selectLatestBudgetRecordByUserId(Long user_id);

    @Result(column = "record_date", property = "record_date")
    @Result(column = "user_id", property = "user_id")
    @Select("select * from BudgetRecords where id = #{id}")
    public BudgetRecord selectBudgetRecordById(Long id);

    @Delete("delete from BudgetRecords where id = #{id}")
    public void deleteById(Long id);

    //得到一个用户id下所有不同的预算category
    @Select("select distinct category from BudgetRecords where user_id = #{user_id}")
    public List<String> selectCategoryByUserId(Long user_id);

    //得到一个用户id下某个category的预算总和
    @Select("select sum(amount) from BudgetRecords where user_id = #{user_id} and category = #{category}")
    public Integer selectSumByCategory(Long user_id, String category);

    @Select("select sum(amount) from BudgetRecords where user_id = #{user_id}")
    public Integer selectSumByUserId(Long user_id);

    @Insert("insert into BudgetRecords(user_id, record_date, name, amount, description, category, gain, tags) values(#{user_id}, #{record_date}, #{name}, #{amount}, #{description}, #{category}, #{gain}, #{tags})")
    public void insert(BudgetRecord budgetRecord);
}
