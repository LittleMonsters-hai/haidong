package com.haidong.storysharing.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Path {
    /**
     * 路径id
     */
    private Integer id;

    /**
     * 访问路径
     */
    private String path;

    /**
     * 角色集合
     */
    private List<Role> roles;
}
