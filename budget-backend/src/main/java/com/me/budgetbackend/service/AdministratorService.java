package com.me.budgetbackend.service;

import com.me.budgetbackend.entity.*;
import com.me.budgetbackend.exceptions.AdminAlreadyExistException;
import com.me.budgetbackend.exceptions.AdminNotFoundException;
import com.me.budgetbackend.exceptions.UserNotFoundException;
import com.me.budgetbackend.mapper.AdministratorMapper;
import com.me.budgetbackend.mapper.ServerStatMapper;
import com.me.budgetbackend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdministratorService {
    @Autowired
    private AdministratorMapper administratorMapper;
    @Autowired
    private ServerStatMapper serverStatMapper;

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

    public List<NewUserStat> newUsers(String token) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        Administrator user = administratorMapper.selectByUsername(username);
        if(user == null)
            throw new AdminNotFoundException("未找到用户");
        List<ServerStat> list = serverStatMapper.selectAllStats();
        List<NewUserStat> newUsers = new ArrayList<>();
        for(ServerStat serverStat : list) {
            NewUserStat newUserStat = new NewUserStat(serverStat);
            newUsers.add(newUserStat);
        }
        return newUsers;
    }

    public List<TotalUserStat> totalUsers(String token) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        Administrator user = administratorMapper.selectByUsername(username);
        if(user == null)
            throw new AdminNotFoundException("未找到用户");
        List<ServerStat> list = serverStatMapper.selectAllStats();
        List<TotalUserStat> totalUserStats = new ArrayList<>();
        for(ServerStat serverStat : list) {
            TotalUserStat totalUserStat = new TotalUserStat(serverStat);
            totalUserStats.add(totalUserStat);
        }
        return totalUserStats;
    }

    public List<TotalRecordStat> totalRecords(String token) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        Administrator user = administratorMapper.selectByUsername(username);
        if(user == null)
            throw new AdminNotFoundException("未找到用户");
        List<ServerStat> list = serverStatMapper.selectAllStats();
        List<TotalRecordStat> totalRecordStats = new ArrayList<>();
        for(ServerStat serverStat : list) {
            TotalRecordStat totalRecordStat = new TotalRecordStat(serverStat);
            totalRecordStats.add(totalRecordStat);
        }
        return totalRecordStats;
    }
}
