package com.tianhuo.thkernel.domain.category.impl;

import com.tianhuo.thcommon.utils.StringUtil;
import com.tianhuo.thkernel.domain.category.Category;
import com.tianhuo.thkernel.domain.category.CategoryService;
import com.tianhuo.thkernel.domain.category.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * user service
 * @see com.tianhuo.thkernel.domain.category.CategoryService
 * @author liguowei
 * @date 2019-12-03 15:24:59
 */
@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  @Override
  public List<Category> list() {
    return categoryRepository.list();
  }

  @Override
  public void add(Category category) {
    if(null == category) {
      return;
    }
    categoryRepository.insert(category);
  }

  @Override
  public void update(Category category) {
    if(null == category) {
      return;
    }
    categoryRepository.update(category);
  }

  @Override
  public void delete(Integer id) {
    if(null == id) {
      return;
    }
    categoryRepository.delete(id);
  }

  @Override
  public Category findById(String id) {
    return categoryRepository.queryById(
        StringUtil.convertToLong(id, 0L).intValue()
    );
  }
}
