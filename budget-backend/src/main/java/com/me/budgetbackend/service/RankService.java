package com.me.budgetbackend.service;

import com.me.budgetbackend.entity.ContinuousRecord;
import com.me.budgetbackend.entity.RankUser;
import com.me.budgetbackend.entity.User;
import com.me.budgetbackend.mapper.ContinuousRecordMapper;
import com.me.budgetbackend.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RankService {
    private static final Logger logger = LoggerFactory.getLogger(RankService.class);
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ContinuousRecordMapper continuousRecordMapper;
    @Autowired
    private UserMapper userMapper;

    public void updateRank(String username, int score) {
        redisTemplate.opsForZSet().incrementScore("rank", username, score);
    }

    public Set<String> getRank() {
        return redisTemplate.opsForZSet().range("rank", 0, 5);
    }

    //定时任务，每天凌晨0点更新排行榜
    @Scheduled(cron = "0 0 0 * * ?")
    public void loadRankFromDB() {
        redisTemplate.delete("rank");
        continuousRecordMapper.selectAll().forEach(continuousRecord -> {
            redisTemplate.opsForZSet()
                    .add("rank", userMapper.selectById(continuousRecord.getUser_id()).getUsername(), continuousRecord.getCount());
            redisTemplate.opsForZSet().add("date", userMapper.selectById(continuousRecord.getUser_id()).getUsername(), continuousRecord.getRecord_date().getTime());
        });
    }

    //定时任务, 每天晚上11点30分写入数据库
    @Scheduled(cron = "0 30 23 * * ?")
    public void saveRankToDB() {
        Set<String> rank = redisTemplate.opsForZSet().reverseRange("rank", 0, 5);
        rank.forEach(username -> {
            continuousRecordMapper.updateCountByUsername(username, redisTemplate.opsForZSet().score("rank", username).intValue());
            continuousRecordMapper.updateRecordDateByUserId(new java.sql.Date(redisTemplate.opsForZSet().score("date", username).longValue()), userMapper.selectByUsername(username).getId());
        });
    }

    public List<RankUser> getRankUsers() {
        List<RankUser> rankUsers = new ArrayList<>();
        Set<String> rank = redisTemplate.opsForZSet().reverseRange("rank", 0, 5);
        rank.forEach(username -> {
            RankUser rankUser = new RankUser();
            rankUser.setUsername(username);
            rankUser.setContinuousRecord(redisTemplate.opsForZSet().score("rank", username).intValue());
            rankUsers.add(rankUser);
        });
        return rankUsers;
    }

    public void setRank(String username, int i) {
        redisTemplate.opsForZSet().add("rank", username, i);
    }

    public void updateLastLogin(String username, Date now) {
        redisTemplate.opsForZSet().add("date", username, now.getTime());
    }

}
