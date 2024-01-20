package com.me.budgetbackend.utils;

public interface ResultCode {
    public static Integer SUCCESS = 20000;
    public static Integer ERROR = 20001;
    public static Integer USER_NOT_FOUND = 20002;
    public static Integer USER_ALREADY_EXISTS = 20003;
    public static Integer ADD_TODO_FAILED = 20004;
    public static Integer GET_AVATAR_FAILED = 30005;
    public static Integer GET_NOTIFICATION_FAILED = 30006;
    public static Integer GET_CONTINUE_FAILED = 30007;
    public static Integer GET_RANK_USERS_FAILED = 30008;
    public static Integer GET_TODO_LIST_FAILED = 30009;
    public static Integer GET_TOTAL_BUDGET_FAILED = 30010;
    public static Integer GET_PIE_CHART_DATA_FAILED = 30011;
    public static Integer GET_BUDGET_LIST_FAILED = 30012;
    public static Integer GET_PROGRESS_FAILED = 30013;
}
