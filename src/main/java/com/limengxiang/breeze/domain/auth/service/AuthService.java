package com.limengxiang.breeze.domain.auth.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.limengxiang.breeze.config.Config;
import com.limengxiang.breeze.domain.auth.model.AuthCredential;
import com.limengxiang.breeze.domain.auth.repository.AuthCredentialRepository;
import com.limengxiang.breeze.http.OpEnum;
import com.limengxiang.breeze.utils.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Component
public class AuthService {

    private final AuthCredentialRepository authCredentialRepository;

    private final LoadingCache<String, AuthCredential> cachePool;

    @Autowired
    public AuthService(Config config, AuthCredentialRepository authCredentialRepo) {
        this.authCredentialRepository = authCredentialRepo;
        cachePool = CacheBuilder.newBuilder()
                .maximumSize(config.getExecutorCacheCapacity())
                .expireAfterWrite(config.getExecutorCacheTime(), TimeUnit.SECONDS)
                .build(
                        new CacheLoader<String, AuthCredential>() {
                            @Override
                            public AuthCredential load(String appId) throws Exception {
                                return AuthService.this.authCredentialRepository.loadCredential(appId);
                            }
                        }
                );
    }

    public AuthCredential getCredential(String appId) {
        if (StrUtil.isEmpty(appId)) {
            return null;
        }
        try {
            return cachePool.get(appId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean canDo(AuthCredential credential, OpEnum op) {
        if (credential.isSuper()) {
            return true;
        }
        if (op == null) {
            return true;
        }
        return credential.hasPrivilege(op.getPrivilege());
    }
}
