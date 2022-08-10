package com.warehouse.workout.constant.code;

public enum UserRoleCode {

    USER("회원"),
    ADMIN("관리자");

    private final String description;

    UserRoleCode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
