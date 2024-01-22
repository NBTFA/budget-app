package com.me.budgetbackend.entity;

import java.util.List;

public class InitializeBudgetRequest {
    private String totalBudget;
    private List<BudgetCategory> budgetCategories;

    public String getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(String totalBudget) {
        this.totalBudget = totalBudget;
    }

    public List<BudgetCategory> getBudgetCategories() {
        return budgetCategories;
    }

    public void setBudgetCategories(List<BudgetCategory> budgetCategories) {
        this.budgetCategories = budgetCategories;
    }
}

