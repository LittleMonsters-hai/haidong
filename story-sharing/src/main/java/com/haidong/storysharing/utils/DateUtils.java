package com.haidong.storysharing.utils;

import org.springframework.context.annotation.Bean;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/02/23/11:29
 * @Description:
 */
public class DateUtils {
    public String formatDateTime(LocalDateTime ldf){
        String dateTime = ldf.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return dateTime;
    }

    public static String formatDate(LocalDateTime ldf){
        String date = ldf.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return date;
    }
    public static String formatTime(LocalDateTime ldf){
        String time = ldf.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return time;
    }
}
