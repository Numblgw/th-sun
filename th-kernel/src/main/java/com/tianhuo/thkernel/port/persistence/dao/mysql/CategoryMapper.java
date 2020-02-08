package com.tianhuo.thkernel.port.persistence.dao.mysql;

import com.tianhuo.thkernel.port.persistence.entity.CategoryDO;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * category dao
 * @author liguowei
 * @date 2019-12-02 20:43:59
 */
@Mapper
public interface CategoryMapper {

  /**
   * get category list
   * @return category list
   */
  @Select("select id, name from `category`")
  List<CategoryDO> list();

  /**
   * update category
   * @param category category to update
   */
  @Update("update `category` "
      + "set name = #{name} "
      + "where id = #{id}")
  void update(CategoryDO category);

  /**
   * delete category
   * @param id category id to delete
   */
  @Delete("delete from `category` where id = #{id}")
  void delete(Integer id);

  /**
   * insert one category
   * @param category category to save
   */
  @Insert("insert into `category`(name) "
      + "values(#{name})")
  void insert(CategoryDO category);

  /**
   * query by id
   * @param id category id
   * @return category data object
   */
  @Select("select id, name from `category` where id = #{id} ")
  CategoryDO queryById(Integer id);
}
