package com.limengxiang.breeze.auth;

import com.limengxiang.breeze.consts.ApiConst;
import com.limengxiang.breeze.consts.AuthConst;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class AuthHelper {

    private enum OpEnum {
        job_create(AuthConst.JOB_PRIV_CREATE),
        job_delete(AuthConst.JOB_PRIV_DELETE),
        job_update(AuthConst.JOB_PRIV_UPDATE),
        exec_create(AuthConst.EXEC_PRIV_CREATE),
        exec_delete(AuthConst.EXEC_PRIV_DELETE),
        exec_update(AuthConst.EXEC_PRIV_UPDATE),
        ;

        OpEnum(int privilege) {
            this.privilege = privilege;
        }

        int privilege;
    }

    public static boolean canDo(AuthCredential credential, HttpServletRequest request) {
        if (credential.isSuper()) {
            return true;
        }
        OpEnum op = getOp(request);
        if (op == null) {
            return true;
        }
        return credential.hasPrivilege(op.privilege);
    }

    private static OpEnum getOp(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) {
            return null;
        }
        switch (request.getRequestURI()) {
            case ApiConst.URI_JOB_CREATE:
                return OpEnum.job_create;
            case ApiConst.URI_JOB_DELETE:
                return OpEnum.job_delete;
            case ApiConst.URI_JOB_UPDATE:
            case ApiConst.URI_JOB_DISABLE:
            case ApiConst.URI_JOB_RECALL:
                return OpEnum.job_update;
            case ApiConst.URI_EXEC_CREATE:
                return OpEnum.exec_create;
            case ApiConst.URI_EXEC_DELETE:
                return OpEnum.exec_delete;
            case ApiConst.URI_EXEC_UPDATE:
            case ApiConst.URI_EXEC_DISABLE:
            case ApiConst.URI_EXEC_RECALL:
                return OpEnum.exec_update;
            default:
                return null;
        }
    }

}
