package com.tianhuo.thkernel.domain.category.repository;

import com.tianhuo.sunshine.dto.CategoryDto;
import com.tianhuo.thcommon.utils.StringUtil;
import com.tianhuo.thkernel.domain.category.Category;
import com.tianhuo.thkernel.port.persistence.entity.CategoryDO;

/**
 * category to category data object converter
 * @author liguowei
 * @date 2019-12-03 15:29:32
 */
public class CategoryConverter {

  /**
   * CategoryDO to Category
   * @param category category data object
   * @return category domain object
   */
  public static Category convert(CategoryDO category) {
    return new Category(
        category.getId(),
        category.getName()
    );
  }

  public static CategoryDO convert(Category category) {
    CategoryDO categoryEntity = new CategoryDO();
    categoryEntity.setId(category.getId());
    categoryEntity.setName(category.getName());
    return categoryEntity;
  }

  public static CategoryDto toDto(Category category) {
    return CategoryDto.builder()
        .id(String.valueOf(category.getId()))
        .name(category.getName())
        .build();
  }

  public static Category convert(CategoryDto categoryDto) {
    return new Category(
        StringUtil.convertToLong(categoryDto.getId(), 0L).intValue(),
        categoryDto.getName()
    );
  }
}
