package com.me.budgetbackend.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Table(name = "ServerStats")
public class ServerStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private Integer new_users;
    private Integer total_users;
    private Integer total_records;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getNew_users() {
        return new_users;
    }

    public void setNew_users(Integer new_users) {
        this.new_users = new_users;
    }

    public Integer getTotal_users() {
        return total_users;
    }

    public void setTotal_users(Integer total_users) {
        this.total_users = total_users;
    }

    public Integer getTotal_records() {
        return total_records;
    }

    public void setTotal_records(Integer total_records) {
        this.total_records = total_records;
    }
}
