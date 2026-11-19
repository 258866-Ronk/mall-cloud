package com.mallcloud.user.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper; import com.mallcloud.user.entity.UserEntity; import com.mallcloud.user.mapper.UserMapper;
import org.springframework.boot.CommandLineRunner; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class AdminInitializer implements CommandLineRunner {
    private final UserMapper mapper; private final PasswordEncoder encoder;
    public AdminInitializer(UserMapper mapper, PasswordEncoder encoder) { this.mapper = mapper; this.encoder = encoder; }
    @Override public void run(String... args) {
        if (mapper.selectCount(new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getUsername, "admin")) == 0) {
            UserEntity user = new UserEntity(); user.setUsername("admin"); user.setPassword(encoder.encode("Admin@123456")); user.setPhone("13800000000");
            user.setEmail("admin@mall.local"); user.setStatus(1); user.setRole("ADMIN"); user.setCreateTime(LocalDateTime.now()); user.setUpdateTime(LocalDateTime.now()); mapper.insert(user);
        }
    }
}
