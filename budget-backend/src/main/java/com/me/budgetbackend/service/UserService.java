package com.me.budgetbackend.service;

import com.me.budgetbackend.entity.User;
import com.me.budgetbackend.exceptions.UserNotFoundException;
import com.me.budgetbackend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public Boolean Login(User user) {
        User user1 = userMapper.login(user);
        if(user1 == null)
            throw new UserNotFoundException("未找到用户");
        else
            return true;
    }
}
