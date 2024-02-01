package com.me.budgetbackend.service;

import com.me.budgetbackend.entity.Administrator;
import com.me.budgetbackend.entity.User;
import com.me.budgetbackend.exceptions.AdminAlreadyExistException;
import com.me.budgetbackend.exceptions.AdminNotFoundException;
import com.me.budgetbackend.exceptions.UserNotFoundException;
import com.me.budgetbackend.mapper.AdministratorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorService {
    @Autowired
    private AdministratorMapper administratorMapper;

    public void login(Administrator admin) {
        Administrator admin1 = administratorMapper.login(admin);
        if(admin1 == null)
            throw new AdminNotFoundException("未找到用户");
    }

    public void register(Administrator admin) {
        Administrator admin1 = administratorMapper.selectByUsername(admin.getUsername());
        if (admin1 == null) {
            admin.setCreated_at(new java.sql.Date(System.currentTimeMillis()));
            administratorMapper.insert(admin);
        }
        else
            throw new AdminAlreadyExistException("用户已存在");
    }
}
