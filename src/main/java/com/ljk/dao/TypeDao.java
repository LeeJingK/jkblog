package com.ljk.dao;

import com.ljk.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description: TypeDao
 * author: JKL
 * date: 2021/3/13 15:21
 */
@Mapper
@Repository
public interface TypeDao {

    List<Type> getAllType();

    Type getType(Long id);

    void saveType(Type type);

    Type selectTypeByName(String name);

    void updateType(Type type);

    void deleteType(Long id);

    List<Type> getBlogType();


}
