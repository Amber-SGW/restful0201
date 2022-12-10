package com.ambersgw.restful0201.service.impl;

import com.ambersgw.restful0201.dao.UserDao;
import com.ambersgw.restful0201.dto.UserLoginRequest;
import com.ambersgw.restful0201.dto.UserRegisterRequest;
import com.ambersgw.restful0201.model.User;
import com.ambersgw.restful0201.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    //制式寫法，只有變數會動
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {

        //檢查註冊email
        User user= userDao.getUserByEmail(userRegisterRequest.getEmail());

        //若帳號已被註冊，噴出400exception, 並且不執行return
        if(user != null){
            //{ }會帶入後面參數的值
            log.warn("該email{} 已被註冊", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }


        //創建帳號
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());
        if(user == null){
            log.warn("該email{}尚未註冊",userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if(user.getPassword().equals(userLoginRequest.getPassword())){
            return user;
        }else {
            log.warn("email{}的密碼不正確",userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }
}
