package com.me.budgetbackend.mapper;

import com.me.budgetbackend.entity.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
    @Insert("insert into Categories(user_id, category) values(#{user_id}, #{category})")
    public void insert(Category category);
}
