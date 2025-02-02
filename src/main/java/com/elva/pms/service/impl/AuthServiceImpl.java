package com.elva.pms.service.impl;

import com.elva.pms.pojo.request.AuthRequest;
import com.elva.pms.service.AuthService;
import com.elva.pms.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${owner.username}")
    private String configUsername;

    @Value("${owner.password}")
    private String configPassword;


    @Override
    public String login(AuthRequest authRequest) {

        if (!configUsername.equals(authRequest.getUsername()) || !configPassword.equals(authRequest.getPassword())) {
            throw new RuntimeException("Invalid username or password!");
        }

        return JwtUtil.generateToken(authRequest.getUsername());
    }
}
