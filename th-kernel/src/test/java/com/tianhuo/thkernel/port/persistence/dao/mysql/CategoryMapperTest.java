package com.tianhuo.thkernel.port.persistence.dao.mysql;


import com.tianhuo.thkernel.ThKernelApplication;
import com.tianhuo.thkernel.port.persistence.entity.CategoryDO;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ThKernelApplication.class)
class CategoryMapperTest {

  @Autowired
  private CategoryMapper categoryMapper;

  @Test
  void list() {
    System.out.println(categoryMapper.list());
  }

  @Test
  void update() {
    CategoryDO categoryDO = new CategoryDO();
    categoryDO.setId(1);
    categoryDO.setName("JavaSE");
    categoryMapper.update(categoryDO);
  }

  @Test
  void delete() {
    categoryMapper.delete(3);
  }

  @Test
  void insert() {
    CategoryDO categoryDO = new CategoryDO();
    categoryDO.setName("mysql");
    categoryMapper.insert(categoryDO);
  }
}