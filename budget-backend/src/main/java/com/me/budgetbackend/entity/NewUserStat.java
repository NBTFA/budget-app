package com.me.budgetbackend.entity;

import java.sql.Date;

public class NewUserStat {
    private Date date;
    private Integer value;

    public NewUserStat(ServerStat serverStat)
    {
        this.date = serverStat.getDate();
        this.value = serverStat.getNew_users();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
