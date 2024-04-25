package com.me.budgetbackend.service;

import com.me.budgetbackend.controller.ChatController;
import com.me.budgetbackend.entity.*;
import com.me.budgetbackend.exceptions.UserAlreadyExistException;
import com.me.budgetbackend.exceptions.UserNotFoundException;
import com.me.budgetbackend.mapper.*;
import com.me.budgetbackend.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private BudgetRecordMapper budgetRecordMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    public void login(User user) {
        User user1 = userMapper.login(user);
        if(user1 == null)
            throw new UserNotFoundException("未找到用户");
        //获取当前日期
        Date date = new Date();
        java.sql.Date now = new java.sql.Date(date.getTime());
        //获取用户的上次登录时间
        java.sql.Date last_login = continuousRecordMapper.selectRecordDateByUserId(user1.getId());
        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(last_login.toLocalDate(), now.toLocalDate());
        Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);
        logger.info("相差："+String.valueOf(daysBetween)+"天");
        //如果上次登录时间是昨天，那么连续登录天数加一
        if(last_login != null && daysBetween==1)
        {
            continuousRecordMapper.updateCountByUserId(continuousRecordMapper.selectCountByUserId(user1.getId()) + 1, user1.getId());
        }
        //否则连续登录天数置为1
        else if(last_login != null && daysBetween>1)
        {
            continuousRecordMapper.updateCountByUserId(1, user1.getId());
        }
        //更新用户的上次登录时间
        continuousRecordMapper.updateRecordDateByUserId(now, user1.getId());
    }

    public void register(User user)
    {
        User user1 = userMapper.selectByUsername(user.getUsername());
        if(user1 == null)
        {
            user.setTotal_budget(-1);
            user.setUsed_budget(0);
            user.setCreated_at(new java.sql.Date(System.currentTimeMillis()));
            userMapper.insert(user);
            user1 = userMapper.selectByUsername(user.getUsername());

            ContinuousRecord continuousRecord = new ContinuousRecord();
            continuousRecord.setUser_id((int) user1.getId().doubleValue());
            continuousRecord.setCount(0);
            continuousRecord.setRecord_date(new java.sql.Date(System.currentTimeMillis()));
            continuousRecordMapper.insert(continuousRecord);

            Notification notification = new Notification();
            notification.setUser_id((int) user1.getId().doubleValue());
            notification.setMessage("欢迎使用Budget");
            notification.setFrom_user_name("系统");
            notification.setIs_read(false);
            notification.setCreated_at(new java.sql.Date(System.currentTimeMillis()));
            notificationMapper.insert(notification);

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

    public List<RankUser> getRankList(String token)
    {
        List<RankUser> users = new ArrayList<>();
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        if(user == null)
            throw new UserNotFoundException("未找到用户");
        Integer[] list = continuousRecordMapper.selectTopFiveUserId();
        for(Integer i : list)
        {
            RankUser rankUser = new RankUser(userMapper.selectById(i),
                    i, continuousRecordMapper.selectCountByUserId(i.longValue()));
            users.add(rankUser);
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

    public int getTotalBudget(String token) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        if (user == null)
            throw new UserNotFoundException("未找到用户");
        return userMapper.selectTotalBudgetByUsername(username);
    }

    public List<PieChartData> getPieChartData(String token) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        if (user == null)
            throw new UserNotFoundException("未找到用户");
        List<PieChartData> pieChartDataList = new ArrayList<>();
        List<String> categories = budgetRecordMapper.selectCategoryByUserId(user.getId());
        for(String category : categories)
        {
            int sum = budgetRecordMapper.selectSumByCategory(user.getId(), category);
            PieChartData pieChartData = new PieChartData(category, sum);
            pieChartDataList.add(pieChartData);
        }
        return pieChartDataList;
    }

    public List<BudgetRecord> getBudgetList(String token) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        if (user == null)
            throw new UserNotFoundException("未找到用户");
        return budgetRecordMapper.selectBudgetRecordByUserId(user.getId());
    }

    public int getProgress(String token) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        if (user == null)
            throw new UserNotFoundException("未找到用户");
        int used = 0;
        if(budgetRecordMapper.selectSumByUserId(user.getId())!=null)
            used = budgetRecordMapper.selectSumByUserId(user.getId());
        int total = userMapper.selectTotalBudgetByUsername(username);
        return total==0 ? 0 : used * 100 / total;
    }

    public void addTodo(TodoListRecord todoListRecord, String token) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        if (user == null)
            throw new UserNotFoundException("未找到用户");
        todoListRecord.setUser_id(user.getId());
        todoListRecordMapper.insert(todoListRecord);
    }

    public void setBudgetInfo(String token, String totalBudget, List<BudgetCategory> categories) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        if(user == null)
            throw new UserNotFoundException("未找到用户");
        int budget = Integer.parseInt(totalBudget);
        userMapper.updateTotalBudgetByUsername(budget, username);
        for(BudgetCategory c : categories)
        {
            Category category = new Category();
            category.setUser_id((int) user.getId().doubleValue());
            category.setCategory(c.getName());
            categoryMapper.insert(category);
        }
    }

    public void deleteTodoById(Long id, String token) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        if(user == null)
            throw new UserNotFoundException("未找到用户");
        todoListRecordMapper.deleteById(id);
    }

    public void completeTodoById(Long id, String token) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        if(user == null)
            throw new UserNotFoundException("未找到用户");
        TodoListRecord todoListRecord = todoListRecordMapper.selectById(id);
        todoListRecord.setCompleted(true);
        todoListRecord.setCompleted_Date(new java.sql.Date(System.currentTimeMillis()));
        todoListRecordMapper.completeById(todoListRecord);
    }

    public BudgetRecord addBudget(BudgetRecord budgetRecord, String token) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        if(user == null)
            throw new UserNotFoundException("未找到用户");
        budgetRecord.setUser_id(user.getId());
        budgetRecordMapper.insert(budgetRecord);
        BudgetRecord bR = budgetRecordMapper.selectLatestBudgetRecordByUserId(user.getId());
        Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);
        logger.info(bR.getId().toString());
        return bR;
    }

    public void deleteBudgetById(Long id, String token) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        if(user == null)
            throw new UserNotFoundException("未找到用户");
        budgetRecordMapper.deleteById(id);
    }

    public String getUserName(String token) {
        return JwtUtils.getClaimsByToken(token).getSubject();
    }

    public Long getUserID(String token) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        if(user == null)
            throw new UserNotFoundException("未找到用户");
        return user.getId();
    }

    public boolean verifyEmail(VerifyRequest verifyRequest) {
        String emailKey = "email_code_"+verifyRequest.getEmail();
        logger.info("code: "+verifyRequest.getCode());
        logger.info("emailKey: "+emailKey);
        String codeInRedis = (String) redisTemplate.opsForValue().get(emailKey);
        logger.info("code: "+verifyRequest.getCode());
        logger.info("codeInRedis: "+codeInRedis);
        if(codeInRedis == null)
            return false;
        logger.info("code: "+verifyRequest.getCode());
        logger.info("codeInRedis: "+codeInRedis);
        return verifyRequest.getCode().equals(codeInRedis);
    }
}
