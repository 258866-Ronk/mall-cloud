package com.mallcloud.api.client;

import com.mallcloud.api.user.UserInternalView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "mall-user-service", path = "/internal/users")
public interface UserClient {
    @GetMapping("/by-username/{username}")
    UserInternalView findByUsername(@PathVariable("username") String username);
}
