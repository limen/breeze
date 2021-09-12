package com.limengxiang.breeze.admin.aop;

import com.limengxiang.breeze.SpringContextUtil;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class PointHandlerFactory {

    public static PointHandler getHandler(PointType pointType) {
        switch (pointType) {
            case auth:
                return SpringContextUtil.getBean(AuthPointHandler.class);
            case log:
                return SpringContextUtil.getBean(LogPointHandler.class);
            case audit:
                return SpringContextUtil.getBean(AuditPointHandler.class);
            default:
                throw new RuntimeException("Unsupported point type " + pointType);
        }
    }
}
