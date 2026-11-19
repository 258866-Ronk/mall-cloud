package com.mallcloud.admin.config;
import com.mallcloud.common.security.*; import feign.RequestInterceptor; import org.springframework.context.annotation.*;
@Configuration public class AdminFeignConfig {
 @Bean RequestInterceptor identityForwarder(){return template->{AuthenticatedUser u=UserContext.require();template.header("X-User-Id",u.userId().toString());template.header("X-Username",u.username());template.header("X-User-Role",u.role());};}
}
