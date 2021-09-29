package com.limengxiang.breeze.model;

import com.limengxiang.breeze.http.auth.AuthCredential;
import com.limengxiang.breeze.model.dao.AppCredentialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Service
public class AppCredentialService {

    private AppCredentialMapper appCredentialMapper;

    @Autowired
    public AppCredentialService(AppCredentialMapper appCredentialMapper) {
        this.appCredentialMapper = appCredentialMapper;
    }

    public AuthCredential loadCredential(String appId) {
        return appCredentialMapper.loadCredential(appId);
    }

}
