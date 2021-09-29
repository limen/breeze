package com.limengxiang.breeze.http.auth;

import lombok.Data;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Data
public class AuthCredential {

    private String token;
    private int privilege;

    public boolean isSuper() {
        return (privilege & AuthPrelude.PR_SUPER) == AuthPrelude.PR_SUPER;
    }

    public boolean hasPrivilege(int priv) {
        return (this.privilege & priv) == priv;
    }

}
