package com.haidong.storysharing.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/02/23/11:52
 * @Description:
 *  执行相关操作后的返回结果，并携带相关的执行
 */
@Data
public class Result<T> {

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回的数据
     */
    private T data;

    /**
     * 添加动态的数据
     */
    private Map map = new HashMap();

    public static <T> Result<T> success(T object){
        //钻石表达式
        Result<T> result = new Result<>();
        result.data = object;
        return result;
    }

    /**
     * 返回增强返回结果
     * @param code
     * @param message
     * @param date
     * @param map
     * @param <T>
     * @return
     */
    public static <T> Result<T> restResult(Integer code, String message, T date, Map map){
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setData(date);
        result.setMessage(message);
        result.setMap(map);
        return result;
    }
}
