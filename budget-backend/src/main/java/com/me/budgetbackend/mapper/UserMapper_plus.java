package com.me.budgetbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.me.budgetbackend.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper_plus extends BaseMapper<User> {

}
