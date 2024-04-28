package com.me.budgetbackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

@Setter
@Getter
@Table(name = "notifications")
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer user_id;
    private String message;
    private Boolean is_read;
    private Date created_at;
    private String from_user_name;

}
