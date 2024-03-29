package com.me.budgetbackend.mapper;

import com.me.budgetbackend.entity.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Insert("insert into Categories(user_id, category) values(#{user_id}, #{category})")
    public void insert(Category category);

    @Result(column = "user_id", property = "user_id")
    @Select("select * from Categories")
    public List<Category> selectAll();
}
