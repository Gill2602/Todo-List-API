package com.gll.todoapi.utils;

public final class AppUtils {

    public static boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

}
