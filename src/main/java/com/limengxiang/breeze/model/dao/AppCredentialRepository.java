package com.limengxiang.breeze.model.dao;

import com.limengxiang.breeze.http.auth.AuthCredential;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Mapper
public interface AppCredentialRepository {

    AuthCredential loadCredential(@Param("appId") String appId);

}
