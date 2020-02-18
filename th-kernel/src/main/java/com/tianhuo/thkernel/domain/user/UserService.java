package com.tianhuo.thkernel.domain.user;

import java.util.Collection;
import java.util.List;

/**
 * 用户领域模型
 * @author liguowei
 * @date 2019-11-23 17:35:13
 */
public interface UserService {

  /**
   * add one user
   * @param user user to add
   * @return if success return Boolean.TRUE else return Boolean.FALSE
   */
  Long add(User user);

  /**
   * update one user info
   * @param user user
   */
  void update(User user);

  /**
   * get user info by id
   * @param id user id
   * @return user domain object
   */
  User getById(String id);

  /**
   * get user info by username
   * @param username username
   * @return user domain object
   */
  User getByUsername(String username);

  /**
   * mark delete
   * @param id user id
   */
  void delete(String id);

  /**
   * query user by user ids
   * @param ids collection of user id
   * @return list of user domain object
   */
  List<User> queryUsers(Collection<String> ids);

  /**
   * query user list
   * @return list of user
   */
  List<User> userList();

  /**
   * grant
   * @param uid 用户 id
   * @param roleId 角色 id
   * @return success true
   */
  boolean grant(String uid, String roleId);
}
