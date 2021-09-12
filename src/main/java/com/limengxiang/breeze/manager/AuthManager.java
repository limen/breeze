package com.limengxiang.breeze.manager;

import com.limengxiang.breeze.auth.AuthCredential;
import com.limengxiang.breeze.model.AppCredentialModel;
import com.limengxiang.breeze.utils.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Component
public class AuthManager {

    @Autowired
    private AppCredentialModel appCredentialModel;

    private static class CredentialItem {
        private long loadTs;
        private AuthCredential credential;
    }

    private ConcurrentHashMap<String, CredentialItem> cachePool;

    public AuthManager() {
        cachePool = new ConcurrentHashMap<>();
    }

    public AuthCredential getCredential(String appId) {
        if (StrUtil.isEmpty(appId)) {
            return null;
        }
        long ts = System.currentTimeMillis();
        CredentialItem credential = cachePool.get(appId);
        if (credential == null || isCredentialCacheExpired(ts, credential)) {
            refreshCredential(appId);
        }
        return cachePool.get(appId).credential;
    }

    private boolean isCredentialCacheExpired(long now, CredentialItem item) {
        return now - item.loadTs > 120000;
    }

    private void refreshCredential(String appId) {
        AuthCredential credential = appCredentialModel.loadCredential(appId);
        CredentialItem tk = new CredentialItem();
        tk.loadTs = System.currentTimeMillis();
        tk.credential = credential;
        cachePool.put(appId, tk);
    }

}
