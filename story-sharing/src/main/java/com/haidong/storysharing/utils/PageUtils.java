package com.haidong.storysharing.utils;

import com.haidong.storysharing.entry.Page;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * 分页工具类
 *
 * @Author: HaiDong
 * @Date: 2023/04/07/1:58
 * @Description:
 */
public class PageUtils {
    private static final ThreadLocal<Page> PAGE_HOLDER = new ThreadLocal<>();

    public static void setCurrentPage(Page page){
        PAGE_HOLDER.set(page);
    }

    public static Page getPage(){
        Page page = PAGE_HOLDER.get();
        if (Objects.isNull(page)){
            setCurrentPage(new Page());
        }
        return PAGE_HOLDER.get();
    }

    public static Integer getCurrent(){
        return getPage().getCurrent();
    }

    public static Integer getLength(){
        return getPage().getLength();
    }

    public static Integer getLimitCurrent() {
        return (getCurrent() - 1) * getLength();
    }
}
