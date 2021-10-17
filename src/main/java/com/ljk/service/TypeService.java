package com.ljk.service;

import com.ljk.pojo.Type;

import java.util.List;

/**
 * description: TypeService
 * author: JKL
 * date: 2021/2/22 14:53
 */
public interface TypeService {

    List<Type> getAllType();

    Type getType(Long id);

    void saveType(Type type);

    Type getTypeByName(String name);

    void updateType(Type type);

    void deleteType(Long id);

    List<Type> getBlogType();


}
