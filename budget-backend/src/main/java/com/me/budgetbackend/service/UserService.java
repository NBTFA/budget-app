package com.me.budgetbackend.service;

import com.me.budgetbackend.entity.*;
import com.me.budgetbackend.exceptions.UserAlreadyExistException;
import com.me.budgetbackend.exceptions.UserNotFoundException;
import com.me.budgetbackend.mapper.*;
import com.me.budgetbackend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private BudgetRecordMapper BudgetRecordMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    public void login(User user) {
        User user1 = userMapper.login(user);
        if(user1 == null)
            throw new UserNotFoundException("未找到用户");
        //获取当前时间
        Date date = new Date();
        java.sql.Date now = new java.sql.Date(date.getTime());
        //获取用户的上次登录时间
        java.sql.Date last_login = continuousRecordMapper.selectRecordDateByUserId(user1.getId());
        //如果上次登录时间是昨天，那么连续登录天数加一
        if(last_login != null && last_login.getTime() == now.getTime() - 24 * 60 * 60 * 1000)
        {
            continuousRecordMapper.updateCountByUserId(continuousRecordMapper.selectCountByUserId(user1.getId()) + 1, user1.getId());
        }
        //否则连续登录天数置为1
        else
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
        List<String> categories = BudgetRecordMapper.selectCategoryByUserId(user.getId());
        for(String category : categories)
        {
            int sum = BudgetRecordMapper.selectSumByCategory(user.getId(), category);
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
        return BudgetRecordMapper.selectBudgetRecordByUserId(user.getId());
    }

    public int getProgress(String token) {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        if (user == null)
            throw new UserNotFoundException("未找到用户");
        int used = 0;
        if(BudgetRecordMapper.selectSumByUserId(user.getId())!=null)
            used = BudgetRecordMapper.selectSumByUserId(user.getId());
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
}
