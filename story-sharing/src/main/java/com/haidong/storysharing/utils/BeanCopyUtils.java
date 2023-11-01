package com.haidong.storysharing.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/03/27/9:12
 * @Description:
 * Bean拷贝工具
 */
public class BeanCopyUtils {
    public static <T> T copyObject(Object source, Class<T> target){
        T tmp = null;
        try {
            //使用类加载实例化对象
            tmp = target.newInstance();
        }catch (Exception e) {
            e.printStackTrace();
        }
        if (source != null){
            //进行属性的拷贝
            BeanUtils.copyProperties(source,tmp);
        }
        //返回拷贝后的对象
        return tmp;
    }

    public static <T,S> List<T> copyList(List<S> source, Class<T> target){
        List<T> list = new ArrayList<>();
        if (source != null && source.size() != 0){
            for (Object o : source) {
                list.add(BeanCopyUtils.copyObject(o, target));
            }
        }
        return list;
    }
}
