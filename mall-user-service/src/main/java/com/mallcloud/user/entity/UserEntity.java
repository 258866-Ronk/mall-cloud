package com.mallcloud.user.entity;

import com.baomidou.mybatisplus.annotation.*; import lombok.Data; import java.time.LocalDateTime;

@Data @TableName("mall_user")
public class UserEntity {
    @TableId(type = IdType.ASSIGN_ID) private Long id;
    private String username; private String password; private String phone; private String email; private Integer status; private String role;
    @TableField(fill = FieldFill.INSERT) private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE) private LocalDateTime updateTime;
}
