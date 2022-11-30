package com.ambersgw.restful0201.service;

import com.ambersgw.restful0201.dto.UserRegisterRequest;
import com.ambersgw.restful0201.model.User;

public interface UserService {

    User getUserById(Integer userId);
    Integer register(UserRegisterRequest userRegisterRequest);
}
