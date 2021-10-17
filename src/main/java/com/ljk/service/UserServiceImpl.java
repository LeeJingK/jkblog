package com.ljk.service;

import com.ljk.dao.UserDao;
import com.ljk.pojo.User;
import com.ljk.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * description: UserServiceImpl
 * author: JKL
 * date: 2021/2/21 15:02
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User checkUser(String username, String password) {
        User user = userDao.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }


    @Override
    public User getUserInfo() {
        return userDao.getUserInfo();
    }

    @Override
    public void updateUserInfo(User user) {
        user.setUpdateTime(new Date());
        userDao.updateUserInfo(user);
    }

    @Override
    public User getUser(Long id) {
        return userDao.getUser(id);
    }

}
