package com.haidong.storysharing.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/04/07/10:51
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page {
    private Integer current = 1;
    private Integer length = 2;
}
