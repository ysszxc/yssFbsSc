package com.qf.service;

import com.qf.entity.User;

/**
 * @author Yss
 * @Date 2019/10/21 0021
 */
public interface IUserService {

    int register(User user);

    User login(String username,String password);

    User queryByUsername(String username);

    int updataPasswordByUserName(String username, String password);
}
