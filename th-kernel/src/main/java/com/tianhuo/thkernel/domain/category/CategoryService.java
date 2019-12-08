package com.tianhuo.thkernel.domain.category;

import java.util.List;

/**
 * category domain service
 * @author liguowei
 * @date 2019-12-02 20:57:08
 */
public interface CategoryService {

  /**
   * category list
   * @return category list
   */
  List<Category> list();

  /**
   * category
   * @param category
   */
  void add(Category category);

  /**
   * update one category
   * @param category category to update
   */
  void update(Category category);

  /**
   * delete one category by id
   * @param id category id
   */
  void delete(Integer id);
}
