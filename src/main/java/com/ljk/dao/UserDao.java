package com.ljk.dao;

import com.ljk.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * description: UserRepository
 * author: JKL
 * date: 2021/2/21 15:04
 */

@Mapper// 告诉springboot这是一个mybatis的mapper类
@Repository //将UserDao交由spring容器管理
public interface UserDao {

    User findByUsernameAndPassword(String username, String password);

    User getUserInfo();

    void updateUserInfo(User user);

    User getUser(Long id);
}
