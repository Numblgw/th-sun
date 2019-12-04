package com.tianhuo.thkernel.domain.user.repository;

import com.tianhuo.thkernel.port.persistence.dao.mysql.UserMapper;
import com.tianhuo.thcommon.domain.User;
import com.tianhuo.thcommon.utils.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * user repository
 * @author liguowei
 * @date 2019-12-03 16:51:49
 */
@Repository
public class UserRepository {

  @Autowired
  private UserMapper userMapper;

  /**
   * add one user
   * @param user user to save
   * @return if success return 1 else return 0
   */
  public int add(User user) {
    return userMapper.insert(UserConverter.convert(user));
  }

  /**
   * query user info by user id
   * @param id user id
   * @return user domain object
   */
  public User queryById(String id) {
    return UserConverter.convert(
        userMapper.findById(StringUtil.convertToLong(id, 0))
    );
  }

  /**
   * query user by username
   * @param username username
   * @return user domain object
   */
  public User queryByUsername(String username) {
    return UserConverter.convert(
        userMapper.findByUsername(username)
    );
  }

  /**
   * update one user
   * @param user user domain object to update
   */
  public void update(User user) {
    userMapper.update(UserConverter.convert(user));
  }

  /**
   * mark delete by user id
   * @param id user id
   */
  public void delete(String id) {
    userMapper.delete(StringUtil.convertToLong(id, 0));
  }
}
