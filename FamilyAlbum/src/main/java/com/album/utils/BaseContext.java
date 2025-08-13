package com.album.utils;

import com.album.entity.Account;

//This class is used to obtain the current thread ID.
public class BaseContext {

    public static ThreadLocal<Account> threadLocal = new ThreadLocal<>();

    public static void setCurrentAccount(Account account) {
        threadLocal.set(account);
    }

    public static Account getCurrentAccount() {
        return threadLocal.get();
    }

    public static void removeCurrentAccount() {
        threadLocal.remove();
    }

}
