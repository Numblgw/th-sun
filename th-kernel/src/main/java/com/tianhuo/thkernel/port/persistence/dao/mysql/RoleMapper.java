package com.tianhuo.thkernel.port.persistence.dao.mysql;

import com.tianhuo.thkernel.port.persistence.entity.RoleDO;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * role mapper
 * the database is mysql
 * @author liguowei
 * @date 2019-12-03 17:17:12
 */
@Mapper
public interface RoleMapper {

  /**
   * add one role
   * @param role role data object to save
   */
  @Insert("insert into `role`(name, description) values(#{name}, #{description})")
  void insert(RoleDO role);

  /**
   * update one role
   * @param role role data object to update
   */
  @Update("update `role` set `name` = #{name}, description = #{description} where id = #{id}")
  void update(RoleDO role);

  /**
   * find one role by id
   * @param id role id
   * @return role data object
   */
  @Select("select id, name, description from `role` where id = #{id}")
  RoleDO findById(Integer id);

  /**
   * get role list
   * @return role data object list
   */
  @Select("select id, name, description from `role`")
  List<RoleDO> list();

  /**
   * delete role by id
   * @param id id
   * @return success 1
   */
  @Delete("delete from `role` where id = #{id}")
  int delete(Integer id);
}
