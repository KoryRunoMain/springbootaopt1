package ru.koryruno.springbootaopt1.utils;

public class UserContext {

    private static ThreadLocal<String> USERNAME = new ThreadLocal<>();

    public UserContext() {
    }

    public static String getUSERNAME() {
        return USERNAME.get();
    }

    public static void setUSERNAME(final String username) {
        USERNAME.set(username);
    }

}
