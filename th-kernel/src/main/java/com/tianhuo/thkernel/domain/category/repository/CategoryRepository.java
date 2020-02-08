package com.tianhuo.thkernel.domain.category.repository;

import com.tianhuo.thkernel.domain.category.Category;
import com.tianhuo.thkernel.port.persistence.dao.mysql.CategoryMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liguowei
 * @date 2019-12-03 15:26:11
 */
@Repository
public class CategoryRepository {

  @Autowired
  private CategoryMapper categoryMapper;

  /**
   * get CategoryDO list and convert to Category list
   * @return
   */
  public List<Category> list() {
    return categoryMapper.list().stream()
        .map(CategoryConverter::convert)
        .collect(Collectors.toList());
  }

  /**
   * insert one category
   * @param category category to save
   */
  public void insert(Category category) {
    categoryMapper.insert(CategoryConverter.convert(category));
  }

  /**
   * update one category
   * @param category category to update
   */
  public void update(Category category) {
    categoryMapper.update(CategoryConverter.convert(category));
  }

  /**
   * delete one category by id
   * @param id category id to delete
   */
  public void delete(Integer id) {
    categoryMapper.delete(id);
  }

  /**
   * query by id
   * @param id category id
   * @return category domain object
   */
  public Category queryById(Integer id) {
    return CategoryConverter.convert(categoryMapper.queryById(id));
  }
}
