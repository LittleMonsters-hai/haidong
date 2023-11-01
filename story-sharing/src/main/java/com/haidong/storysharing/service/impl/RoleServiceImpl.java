package com.haidong.storysharing.service.impl;

import com.haidong.storysharing.dao.RoleMapper;
import com.haidong.storysharing.entry.Role;
import com.haidong.storysharing.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/03/12/2:35
 * @Description:
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired(required = false)
    RoleMapper roleMapper;
    @Override
    public List<Role> getUserRolesByUid(Integer uid) {
        List<Role> roles = roleMapper.getUserRolesByUid(uid);
        return roles;
    }

    @Override
    public int insertOne(Integer uid, Integer rid) {
        return roleMapper.insertOne(uid, rid);
    }

    @Override
    public int removeOneByUserId(Integer uid) {
        return roleMapper.removeOneByUserId(uid);
    }
}
