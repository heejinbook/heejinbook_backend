package com.book.heejinbook.security;

public class AuthHolder {
    private static final ThreadLocal<Long> userIdxHolder = new ThreadLocal<>();

    public static void setUserId(Long userId) {
        userIdxHolder.set(userId);
    }

    public static Long getUserId() {
        return userIdxHolder.get();
    }

    public static void clearUserIdx() {
        userIdxHolder.remove();
    }
}
