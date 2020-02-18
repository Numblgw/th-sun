package com.tianhuo.sunshine.service;

import com.tianhuo.sunshine.dto.CategoryDto;
import com.tianhuo.thcommon.dto.HttpResultWrapper;

import java.util.List;

/**
 * category facade
 * @author liguowei
 * @date 2019-12-22 15:34:10
 */
public interface CategoryFacade {

  /**
   * category dto list
   * @return list of category dto
   */
  HttpResultWrapper<List<CategoryDto>> list();

  /**
   * add category
   * @param categoryDto category dto
   * @return success true
   */
  HttpResultWrapper<Boolean> add(CategoryDto categoryDto);

  /**
   * modify category
   * @param categoryDto category dto
   * @return success true
   */
  HttpResultWrapper<Boolean> update(CategoryDto categoryDto);

  /**
   * delete category
   * @param id category id
   * @return success true
   */
  HttpResultWrapper<Boolean> delete(String id);
}
