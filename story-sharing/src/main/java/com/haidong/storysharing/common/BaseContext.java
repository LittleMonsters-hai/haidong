package com.haidong.storysharing.common;

import com.haidong.storysharing.entry.UserAuthor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/02/24/13:44
 * @Description:
 */
public class BaseContext {
    private static ThreadLocal<UserAuthor> threadLocal = new ThreadLocal<>();

    /**
     *  设置值
     * @param userAuthor
     */
    public static void setCurrentId(UserAuthor userAuthor){
        threadLocal.set(userAuthor);
    }

    /**
     *  获取值
     * @return
     */
    public static UserAuthor getCurrentId(){
        return threadLocal.get();
    }
}
