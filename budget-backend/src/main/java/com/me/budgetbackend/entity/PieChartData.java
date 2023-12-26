package com.me.budgetbackend.entity;

public class PieChartData {
    private String name;
    private Integer value;

    public PieChartData(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public PieChartData() {
    }

    @Override
    public String toString() {
        return "PieChartData{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }
}
