package com.me.budgetbackend.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VerifyRequest {
    private String email;
    private String code;
}
