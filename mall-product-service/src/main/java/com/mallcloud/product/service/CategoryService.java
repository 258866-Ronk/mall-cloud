package com.mallcloud.product.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mallcloud.common.error.BizException; import com.mallcloud.common.error.ErrorCode;
import com.mallcloud.product.dto.CategoryRequest; import com.mallcloud.product.entity.CategoryEntity; import com.mallcloud.product.mapper.CategoryMapper;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryMapper mapper; public CategoryService(CategoryMapper mapper) { this.mapper = mapper; }
    public List<CategoryEntity> list() { return mapper.selectList(new LambdaQueryWrapper<CategoryEntity>().eq(CategoryEntity::getStatus, 1).orderByAsc(CategoryEntity::getSort)); }
    @Transactional public CategoryEntity create(CategoryRequest request) { CategoryEntity entity = fill(new CategoryEntity(), request); mapper.insert(entity); return entity; }
    @Transactional public CategoryEntity update(Long id, CategoryRequest request) { CategoryEntity entity = mapper.selectById(id); if (entity == null) throw new BizException(ErrorCode.NOT_FOUND); fill(entity, request); mapper.updateById(entity); return entity; }
    private CategoryEntity fill(CategoryEntity e, CategoryRequest r) { e.setParentId(r.parentId()); e.setCategoryName(r.categoryName()); e.setLevel(r.level()); e.setSort(r.sort()); e.setStatus(r.status()); return e; }
}
