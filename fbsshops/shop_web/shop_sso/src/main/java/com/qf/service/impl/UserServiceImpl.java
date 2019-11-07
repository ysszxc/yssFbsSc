package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.UserMapper;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Yss
 * @Date 2019/10/21 0021
 */

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int register(User user) {

        //验证是否唯一
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",user.getUsername());
        Integer count = userMapper.selectCount(queryWrapper);

        if (count>0){
            return -1;
        }
        return userMapper.insert(user);
    }

    @Override
    public User login(String username, String password) {
        /*QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",username);
        queryWrapper.eq("password",password);
        return userMapper.selectOne(queryWrapper);*/

        User user = this.queryByUsername(username);

        if (user != null && user.getPassword().equals(password)){
            return user;
        }
        return null;
    }

    @Override
    public User queryByUsername(String username) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",username);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public int updataPasswordByUserName(String username, String password) {
        User user = this.queryByUsername(username);
        user.setPassword(password);
        return userMapper.updateById(user);

    }
}
