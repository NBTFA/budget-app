package com.me.budgetbackend.service;

import com.me.budgetbackend.entity.User;
import com.me.budgetbackend.exceptions.UserAlreadyExistException;
import com.me.budgetbackend.exceptions.UserNotFoundException;
import com.me.budgetbackend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public void login(User user) {
        User user1 = userMapper.login(user);
        if(user1 == null)
            throw new UserNotFoundException("未找到用户");
    }

    public void register(User user)
    {
        User user1 = userMapper.selectByUsername(user.getUsername());
        if(user1 == null)
        {
            userMapper.insert(user);
        }
        else
            throw new UserAlreadyExistException("用户已存在");
    }
}
