package com.limengxiang.breeze.auth;

import com.limengxiang.breeze.consts.AuthConst;
import lombok.Data;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Data
public class AuthCredential {

    private String token;
    private int privilege;

    public boolean isSuper() {
        return (privilege & AuthConst.PRIV_SUPER) == AuthConst.PRIV_SUPER;
    }

    public boolean hasPrivilege(int priv) {
        return (this.privilege & priv) == priv;
    }

}
