package com.limengxiang.breeze.http;

import com.limengxiang.breeze.domain.auth.AuthPrelude;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public enum OpEnum {
    job_create(AuthPrelude.JOB_PR_CREATE),
    job_delete(AuthPrelude.JOB_PR_DELETE),
    job_update(AuthPrelude.JOB_PR_UPDATE),
    exec_create(AuthPrelude.EXEC_PR_CREATE),
    exec_delete(AuthPrelude.EXEC_PR_DELETE),
    exec_update(AuthPrelude.EXEC_PR_UPDATE),
    ;

    OpEnum(int privilege) {
        this.privilege = privilege;
    }

    int privilege;

    public int getPrivilege() {
        return privilege;
    }

    public static OpEnum fromRequest(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) {
            return null;
        }
        switch (request.getRequestURI()) {
            case HttpPrelude.URI_JOB_CREATE:
                return OpEnum.job_create;
            case HttpPrelude.URI_JOB_DELETE:
                return OpEnum.job_delete;
            case HttpPrelude.URI_JOB_UPDATE:
            case HttpPrelude.URI_JOB_DISABLE:
            case HttpPrelude.URI_JOB_RECALL:
                return OpEnum.job_update;
            case HttpPrelude.URI_EXEC_CREATE:
                return OpEnum.exec_create;
            case HttpPrelude.URI_EXEC_DELETE:
                return OpEnum.exec_delete;
            case HttpPrelude.URI_EXEC_UPDATE:
            case HttpPrelude.URI_EXEC_DISABLE:
            case HttpPrelude.URI_EXEC_RECALL:
                return OpEnum.exec_update;
            default:
                return null;
        }
    }

}
