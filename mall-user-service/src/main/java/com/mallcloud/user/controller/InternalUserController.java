package com.mallcloud.user.controller;

import com.mallcloud.api.user.UserInternalView; import com.mallcloud.user.service.UserService; import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/internal/users")
public class InternalUserController {
    private final UserService service; public InternalUserController(UserService service) { this.service = service; }
    @GetMapping("/by-username/{username}") public UserInternalView byUsername(@PathVariable String username) { return service.findInternal(username); }
}
