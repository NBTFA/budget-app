package com.me.budgetbackend.service;

import com.me.budgetbackend.controller.ChatController;
import com.me.budgetbackend.entity.*;
import com.me.budgetbackend.exceptions.AdminAlreadyExistException;
import com.me.budgetbackend.exceptions.AdminNotFoundException;
import com.me.budgetbackend.exceptions.UserNotFoundException;
import com.me.budgetbackend.mapper.*;
import com.me.budgetbackend.messageQueue.RabbitMQSender;
import com.me.budgetbackend.utils.JwtUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class AdministratorService {
    @Autowired
    private AdministratorMapper administratorMapper;
    @Autowired
    private ServerStatMapper serverStatMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BudgetRecordMapper budgetRecordMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ContinuousRecordMapper continuousRecordMapper;
    @Autowired
    private TodoListRecordMapper todoListRecordMapper;
    @Autowired
    private RabbitMQSender rabbitMQSender;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AdministratorService.class);

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

    @Scheduled(cron = "0 59 23 * * ?")
    public void recordDailyStats() {
        int newUsers = userMapper.selectTodayRegisterCount();
        int totalUsers = userMapper.findAll().size();
        int totalRecords = budgetRecordMapper.selectAll().size();
        ServerStat serverStat = new ServerStat();
        serverStat.setNew_users(newUsers);
        serverStat.setTotal_users(totalUsers);
        serverStat.setTotal_records(totalRecords);
        serverStat.setDate(new java.sql.Date(System.currentTimeMillis()));
        serverStatMapper.insert(serverStat);
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

    public List<User> getUser(String token) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        Administrator user = administratorMapper.selectByUsername(username);
        if(user == null)
            throw new AdminNotFoundException("未找到用户");
        return userMapper.findAll();
    }

    public void deleteUser(String token, User user) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        Administrator admin = administratorMapper.selectByUsername(username);
        if(admin == null)
            throw new AdminNotFoundException("未找到用户");
        userMapper.deleteById(user.getId().intValue());
    }

    public void updateUser(String token, User user) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        Administrator admin = administratorMapper.selectByUsername(username);
        if(admin == null)
            throw new AdminNotFoundException("未找到用户");
        userMapper.updateUsernameAndEmailById(user.getUsername(), user.getEmail(), user.getId().intValue());
    }

    public List<Administrator> getAdmins(String token) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        Administrator admin = administratorMapper.selectByUsername(username);
        if(admin == null)
            throw new AdminNotFoundException("未找到用户");
        return administratorMapper.selectAll();
    }

    public boolean getCurrentAdminAuthority(String token) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        Administrator admin = administratorMapper.selectByUsername(username);
        if(admin == null)
            throw new AdminNotFoundException("未找到用户");
        return admin.getRoot();
    }

    public void deleteAdmin(String token, Administrator admin) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        Administrator admin1 = administratorMapper.selectByUsername(username);
        if(admin1 == null)
            throw new AdminNotFoundException("未找到用户");
        Logger logger = Logger.getLogger("deleteAdmin");
        logger.info(admin.toString());
        administratorMapper.deleteById(admin.getId().intValue());
    }

    public void updateAdmin(String token, Administrator admin) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        Administrator admin1 = administratorMapper.selectByUsername(username);
        if(admin1 == null)
            throw new AdminNotFoundException("未找到用户");
        administratorMapper.updateAdmin(admin.getUsername(), admin.getEmail(), admin.getRoot(), admin.getId().intValue());
    }

    public void sendToUser(String token, SendToUserRequest request) throws IOException {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        Administrator admin1 = administratorMapper.selectByUsername(username);
        if(admin1 == null)
            throw new AdminNotFoundException("未找到用户");
        Notification notification = new Notification();
        notification.setCreated_at(new java.sql.Date(System.currentTimeMillis()));
        notification.setFrom_user_name(admin1.getUsername());
        notification.setIs_read(false);
        notification.setMessage(request.getMessage());
        notification.setUser_id(request.getUser_id().intValue());

        DBInstructor<Notification> dbInstructor = new DBInstructor<>();
        dbInstructor.setDbName("Notifications");
        dbInstructor.setOperation("insert");
        dbInstructor.setContent(notification);
        rabbitMQSender.pushToDBQueue(dbInstructor);
    }

    public void sendToAll(String token, String message) throws IOException {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        Administrator admin1 = administratorMapper.selectByUsername(username);
        if(admin1 == null)
            throw new AdminNotFoundException("未找到用户");
        List<User> users = userMapper.findAll();
        for(User user : users) {
            Notification notification = new Notification();
            notification.setCreated_at(new java.sql.Date(System.currentTimeMillis()));
            notification.setFrom_user_name(admin1.getUsername());
            notification.setIs_read(false);
            notification.setMessage(message);
            notification.setUser_id(user.getId().intValue());
            logger.info("message: "+message);
            logger.info("notification: "+notification.getMessage());

            DBInstructor<Notification> dbInstructor = new DBInstructor<>();
            dbInstructor.setDbName("Notifications");
            dbInstructor.setOperation("insert");
            dbInstructor.setContent(notification);
            logger.info("dbInstructor: "+dbInstructor.getContent().getMessage());
            rabbitMQSender.pushToDBQueue(dbInstructor);
        }
    }

    public void deleteNotification(String token, Notification notification) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        Administrator admin1 = administratorMapper.selectByUsername(username);
        if(admin1 == null)
            throw new AdminNotFoundException("未找到用户");
        notificationMapper.deleteById(notification.getId());
    }

    public List<Notification> getNotification(String token) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        Administrator admin1 = administratorMapper.selectByUsername(username);
        if(admin1 == null)
            throw new AdminNotFoundException("未找到用户");
        return notificationMapper.selectAll();
    }

    public List<Category> getCategory(String token) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        Administrator admin1 = administratorMapper.selectByUsername(username);
        if(admin1 == null)
            throw new AdminNotFoundException("未找到用户");
        return categoryMapper.selectAll();
    }

    public List<ContinuousRecord> getContinuous(String token) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        Administrator admin1 = administratorMapper.selectByUsername(username);
        if(admin1 == null)
            throw new AdminNotFoundException("未找到用户");
        return continuousRecordMapper.selectAll();
    }

    public List<BudgetRecord> getBudget(String token) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        Administrator admin1 = administratorMapper.selectByUsername(username);
        if(admin1 == null)
            throw new AdminNotFoundException("未找到用户");
        return budgetRecordMapper.selectAll();
    }

    public void deleteBudget(String token, BudgetRecord budget) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        Administrator admin1 = administratorMapper.selectByUsername(username);
        if(admin1 == null)
            throw new AdminNotFoundException("未找到用户");
        budgetRecordMapper.deleteById(budget.getId());
    }

    public List<TodoListRecord> getTodoList(String token) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        Administrator admin1 = administratorMapper.selectByUsername(username);
        if(admin1 == null)
            throw new AdminNotFoundException("未找到用户");
        return todoListRecordMapper.selectAll();
    }

    public void deleteTodoList(String token, TodoListRecord todo) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        Administrator admin1 = administratorMapper.selectByUsername(username);
        if(admin1 == null)
            throw new AdminNotFoundException("未找到用户");
        todoListRecordMapper.deleteById(todo.getId());
    }
}
