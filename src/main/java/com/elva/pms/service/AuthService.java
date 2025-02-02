package com.elva.pms.service;

import com.elva.pms.pojo.request.AuthRequest;

public interface AuthService {
    String login(AuthRequest authRequest);
}
