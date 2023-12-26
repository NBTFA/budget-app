package com.me.budgetbackend.service;

import com.me.budgetbackend.entity.BudgetRecord;
import com.me.budgetbackend.entity.PieChartData;
import com.me.budgetbackend.mapper.BudgetRecordMapper;
import com.me.budgetbackend.mapper.UserMapper;
import com.me.budgetbackend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetService {
    @Autowired
    private BudgetRecordMapper budgetRecordMapper;
    @Autowired
    private UserMapper userMapper;
    public Integer getProgress(String token)
    {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        Integer total_budget = userMapper.selectByUsername(username).getTotal_budget();
        Integer used_budget = userMapper.selectByUsername(username).getUsed_budget();
        return used_budget * 100 / total_budget;
    }

    public List<BudgetRecord> getAllBudget(String token)
    {
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        Long user_id = userMapper.selectByUsername(username).getId();
        return budgetRecordMapper.selectBudgetRecordByUserId(user_id);
    }

    public List<PieChartData> getPieChart(String token)
    {
        List<PieChartData> pieChartDataList = new ArrayList<>();
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        Long user_id = userMapper.selectByUsername(username).getId();
        List<String> categoryList = budgetRecordMapper.selectCategoryByUserId(user_id);
        for(String category : categoryList)
        {
            Integer sum = budgetRecordMapper.selectSumByCategory(user_id, category);
            PieChartData pieChartData = new PieChartData(category, sum);
            pieChartDataList.add(pieChartData);
        }
        return pieChartDataList;
    }
}
