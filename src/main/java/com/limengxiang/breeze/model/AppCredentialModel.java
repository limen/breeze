package com.limengxiang.breeze.model;

import com.limengxiang.breeze.auth.AuthCredential;
import com.limengxiang.breeze.model.dao.AppCredentialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Service
public class AppCredentialModel {

    @Autowired
    private AppCredentialMapper mapper;

    public AuthCredential loadCredential(String appId) {
        return mapper.loadCredential(appId);
    }

}
