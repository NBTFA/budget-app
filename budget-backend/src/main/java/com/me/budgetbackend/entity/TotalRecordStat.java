package com.me.budgetbackend.entity;

import java.sql.Date;

public class TotalRecordStat {
    private Date date;
    private Integer value;

    public TotalRecordStat(ServerStat serverStat)
    {
        this.date = serverStat.getDate();
        this.value = serverStat.getTotal_records();
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
