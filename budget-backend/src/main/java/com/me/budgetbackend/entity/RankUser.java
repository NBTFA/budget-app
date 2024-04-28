package com.me.budgetbackend.entity;

import nonapi.io.github.classgraph.json.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class RankUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String avatar;
    private Integer continuousRecord;
    private Integer rank;

    public RankUser(User user, Integer rank, Integer continuousRecord)
    {
        this.id = user.getId();
        this.username = user.getUsername();
        this.avatar = user.getAvatar();
        this.continuousRecord = continuousRecord;
        this.rank = rank;
    }

    public RankUser() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getContinuousRecord() {
        return continuousRecord;
    }

    public void setContinuousRecord(Integer continuousRecord) {
        this.continuousRecord = continuousRecord;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
