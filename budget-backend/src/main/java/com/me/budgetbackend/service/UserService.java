package com.me.budgetbackend.service;

import com.me.budgetbackend.entity.ContinuousRecord;
import com.me.budgetbackend.entity.Notification;
import com.me.budgetbackend.entity.TodoListRecord;
import com.me.budgetbackend.entity.User;
import com.me.budgetbackend.exceptions.UserAlreadyExistException;
import com.me.budgetbackend.exceptions.UserNotFoundException;
import com.me.budgetbackend.mapper.ContinuousRecordMapper;
import com.me.budgetbackend.mapper.NotificationMapper;
import com.me.budgetbackend.mapper.TodoListRecordMapper;
import com.me.budgetbackend.mapper.UserMapper;
import com.me.budgetbackend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ContinuousRecordMapper continuousRecordMapper;
    @Autowired
    private TodoListRecordMapper todoListRecordMapper;
    @Autowired
    private NotificationMapper notificationMapper;
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

    public String getAvatar(String token)
    {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        if(user == null)
            throw new UserNotFoundException("未找到用户");
        return user.getAvatar();
    }

    public List<User> getRankList(String token)
    {
        List<User> users = new ArrayList<>();
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        if(user == null)
            throw new UserNotFoundException("未找到用户");
        Integer[] list = continuousRecordMapper.selectTopFiveUserId();
        for(Integer i : list)
        {
            users.add(userMapper.selectById(i));
        }
        return users;
    }

    public List<TodoListRecord> getTodoList(String token)
    {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        if(user == null)
            throw new UserNotFoundException("未找到用户");
        return todoListRecordMapper.selectByUserId(user.getId());
    }

    public Integer getContinue(String token)
    {
        Integer continuous = 0;
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        if(user == null)
            throw new UserNotFoundException("未找到用户");
        continuous = continuousRecordMapper.selectCountByUserId(user.getId());
        return continuous;
    }

    public List<Notification> getNotification(String token)
    {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        if(user == null)
            throw new UserNotFoundException("未找到用户");
        return notificationMapper.selectNotificationByUserId(user.getId());
    }
}
