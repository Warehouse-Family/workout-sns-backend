package com.warehouse.workout.user.entity;

public enum UserRole {

    USER("회원"),
    ADMIN("관리자");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
