package com.warehouse.workout.user.entity;

public enum UserRole {

    USER("일반사용자"),
    ADMIN("관리자");

    private String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
