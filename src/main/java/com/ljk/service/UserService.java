package com.ljk.service;

import com.ljk.pojo.User;

/**
 * description: UserService
 * author: JKL
 * date: 2021/2/21 15:01
 */

public interface UserService {
    User checkUser(String username, String password);

    User getUserInfo();

    void updateUserInfo(User user);

    User getUser(Long id);
}
