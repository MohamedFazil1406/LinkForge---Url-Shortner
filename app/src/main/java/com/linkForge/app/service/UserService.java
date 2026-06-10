package com.linkForge.app.service;

import com.linkForge.app.model.User;


public interface UserService {
    User registerUser(User user);

    String loginUser(User user);


}
