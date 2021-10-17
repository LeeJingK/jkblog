package com.ljk.service;

import com.ljk.dao.TypeDao;
import com.ljk.pojo.Type;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * description: TypeServiceImpl
 * author: JKL
 * date: 2021/2/22 15:11
 */
@Service
public class TypeServiceImpl implements TypeService {
    @Resource
    TypeDao typeDao;

    @Override
    public List<Type> getAllType() {
        return typeDao.getAllType();
    }

    @Override
    public Type getType(Long id) {
        return typeDao.getType(id);
    }

    @Override
    public void saveType(Type type) {
        typeDao.saveType(type);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeDao.selectTypeByName(name);
    }

    @Override
    public void updateType(Type type) {
        typeDao.updateType(type);
    }

    @Override
    public void deleteType(Long id) {
        typeDao.deleteType(id);
    }

    @Override
    public List<Type> getBlogType() {

        return typeDao.getBlogType();
    }
}
