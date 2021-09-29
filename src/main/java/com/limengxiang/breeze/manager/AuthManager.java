package com.limengxiang.breeze.manager;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.limengxiang.breeze.config.Config;
import com.limengxiang.breeze.http.auth.AuthCredential;
import com.limengxiang.breeze.model.AppCredentialService;
import com.limengxiang.breeze.utils.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Component
public class AuthManager {

    private final AppCredentialService appCredentialService;

    private final LoadingCache<String, AuthCredential> cachePool;

    @Autowired
    public AuthManager(Config config, AppCredentialService appCredentialService) {
        this.appCredentialService = appCredentialService;
        cachePool = CacheBuilder.newBuilder()
                .maximumSize(config.getExecutorCacheCapacity())
                .expireAfterWrite(config.getExecutorCacheTime(), TimeUnit.SECONDS)
                .build(
                        new CacheLoader<String, AuthCredential>() {
                            @Override
                            public AuthCredential load(String appId) throws Exception {
                                return AuthManager.this.appCredentialService.loadCredential(appId);
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

}
