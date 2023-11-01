package com.haidong.storysharing.service;

import com.haidong.storysharing.entry.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/03/12/2:34
 * @Description:
 */
public interface RoleService {
    /**
     * 根据用户的id 查询用户所拥有的角色
     * @param uid
     * @return
     */
    List<Role> getUserRolesByUid(Integer uid);

    /**
     * 插入用户信息角色信息
     * @param uid
     * @param rid
     * @return
     */
    int insertOne(@Param("uid") Integer uid, @Param("rid") Integer rid);

    /**
     * 删除用户角色
     * @param uid
     * @return
     */
    int removeOneByUserId(@Param("uid") Integer uid);
}
