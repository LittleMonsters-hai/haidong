package com.haidong.storysharing.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class History {
    private Integer id;

    private Integer userId;

    private Integer storyId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
