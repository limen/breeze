package com.limengxiang.breeze.domain.auth.model;

import com.limengxiang.breeze.domain.auth.AuthPrelude;
import lombok.Data;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Data
public class AuthCredential {

    private String appId;
    private String token;
    private int privilege;

    public boolean isSuper() {
        return (privilege & AuthPrelude.PR_SUPER) == AuthPrelude.PR_SUPER;
    }

    public boolean hasPrivilege(int pr) {
        return (this.privilege & pr) == pr;
    }

}
