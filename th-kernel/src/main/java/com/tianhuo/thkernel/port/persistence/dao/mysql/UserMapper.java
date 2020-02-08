package com.tianhuo.thkernel.port.persistence.dao.mysql;

import com.tianhuo.thkernel.port.persistence.entity.UserDO;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


/**
 * user mapper
 * the database is mysql
 * @author liguowei
 * @date 2019-12-03 16:22:09
 */
@Mapper
public interface UserMapper {

  /**
   * find user by user id
   * @param id user id
   * @return user data object
   */
  @Select("select id, username, password, nickname, registered_time, role_id "
      + "from  `users` "
      + "where id = #{id} and if_delete = 0")
  UserDO findById(Long id);

  /**
   * find user by username
   * @param username username
   * @return user data object
   */
  @Select("select id, username, password, nickname, registered_time, role_id "
      + "from  `users` "
      + "where username = #{username} and if_delete = 0")
  UserDO findByUsername(String username);

  /**
   * insert one user
   * @param user user data object to save
   * @return if success return 1 else return 0
   */
  @Insert("insert into `users`(username, password, nickname, registered_time, role_id) "
      + "values(#{username}, #{password}, #{nickname}, #{registeredTime}, #{roleId})")
  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  Long insert(UserDO user);

  /**
   * update user
   * @param user user data object to update
   */
  @Update("update `users` "
      + "set password = #{password}, nickname = #{nickname} "
      + "where id = #{id} and if_delete = 0")
  void update(UserDO user);

  /**
   * delete user by id
   * @param id user id
   */
  @Delete("update `users` set if_delete = 1 where id = #{id}")
  void delete(Long id);

  /**
   * query users by ids
   * @param ids list of user id
   * @return list of user data object
   */
  @Select("<script>"
      + "select id, username, password, nickname, registered_time, role_id "
      + "from `users` "
      + "where id in "
      + "<foreach item='item' collection='list' open='(' separator=', ' close=')'> "
      + "#{item} "
      + "</foreach> "
      + "and if_delete = 0 "
      + "</script> ")
  List<UserDO> queryUserByIds(List<Integer> ids);
}
