package com.mallcloud.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper; import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.api.user.UserInternalView; import com.mallcloud.api.user.UserSummary; import com.mallcloud.common.api.PageResponse;
import com.mallcloud.common.error.*; import com.mallcloud.user.dto.RegisterRequest; import com.mallcloud.user.entity.UserEntity; import com.mallcloud.user.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class UserService {
    private final UserMapper mapper; private final PasswordEncoder passwordEncoder;
    public UserService(UserMapper mapper, PasswordEncoder passwordEncoder) { this.mapper = mapper; this.passwordEncoder = passwordEncoder; }
    @Transactional public UserSummary register(RegisterRequest request) {
        Long count = mapper.selectCount(new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getUsername, request.username()).or().eq(UserEntity::getPhone, request.phone()));
        if (count > 0) throw new BizException(ErrorCode.USER_EXISTS);
        UserEntity user = new UserEntity(); user.setUsername(request.username()); user.setPassword(passwordEncoder.encode(request.password()));
        user.setPhone(request.phone()); user.setEmail(request.email()); user.setStatus(1); user.setRole("USER"); user.setCreateTime(LocalDateTime.now()); user.setUpdateTime(LocalDateTime.now()); mapper.insert(user);
        return summary(user);
    }
    public UserInternalView findInternal(String username) {
        UserEntity user = mapper.selectOne(new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getUsername, username));
        if (user == null) throw new BizException(ErrorCode.NOT_FOUND, "用户不存在");
        return new UserInternalView(user.getId(), user.getUsername(), user.getPassword(), user.getPhone(), user.getEmail(), user.getStatus(), user.getRole());
    }
    public UserSummary get(Long id) { UserEntity user = mapper.selectById(id); if (user == null) throw new BizException(ErrorCode.NOT_FOUND); return summary(user); }
    public PageResponse<UserSummary> page(long page, long size) {
        Page<UserEntity> result = mapper.selectPage(Page.of(page, size), new LambdaQueryWrapper<UserEntity>().orderByDesc(UserEntity::getCreateTime));
        return PageResponse.of(result.getRecords().stream().map(this::summary).toList(), page, size, result.getTotal());
    }
    @Transactional public UserSummary updateStatus(Long id, int status) {
        if (status != 0 && status != 1) throw new BizException(ErrorCode.BAD_REQUEST, "状态只能是 0 或 1");
        UserEntity user = mapper.selectById(id); if (user == null) throw new BizException(ErrorCode.NOT_FOUND);
        if ("ADMIN".equals(user.getRole()) && status == 0) throw new BizException(ErrorCode.BAD_REQUEST, "不能禁用管理员账号");
        user.setStatus(status); user.setUpdateTime(LocalDateTime.now()); mapper.updateById(user); return summary(user);
    }
    private UserSummary summary(UserEntity u) { return new UserSummary(u.getId(), u.getUsername(), u.getPhone(), u.getEmail(), u.getStatus(), u.getRole(), u.getCreateTime()); }
}
