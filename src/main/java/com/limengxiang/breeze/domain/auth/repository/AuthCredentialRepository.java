package com.limengxiang.breeze.domain.auth.repository;

import com.limengxiang.breeze.domain.auth.model.AuthCredential;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Mapper
public interface AuthCredentialRepository {

    AuthCredential loadCredential(@Param("appId") String appId);

}
