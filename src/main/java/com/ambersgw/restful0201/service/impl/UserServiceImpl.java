package com.ambersgw.restful0201.service.impl;

import com.ambersgw.restful0201.dao.UserDao;
import com.ambersgw.restful0201.dto.UserRegisterRequest;
import com.ambersgw.restful0201.model.User;
import com.ambersgw.restful0201.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }
}
