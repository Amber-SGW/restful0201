package com.ambersgw.restful0201.dao;

import com.ambersgw.restful0201.dto.UserRegisterRequest;
import com.ambersgw.restful0201.model.User;

public interface UserDao {

    User getUserById(Integer userId);
    Integer createUser(UserRegisterRequest userRegisterRequest);
}
