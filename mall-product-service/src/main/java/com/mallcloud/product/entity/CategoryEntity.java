package com.mallcloud.product.entity;

import com.baomidou.mybatisplus.annotation.*; import lombok.Data;

@Data @TableName("product_category")
public class CategoryEntity { @TableId(type=IdType.ASSIGN_ID) private Long id; private Long parentId; private String categoryName; private Integer level; private Integer sort; private Integer status; }
