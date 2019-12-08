package com.tianhuo.thkernel.domain.user.repository;

import com.tianhuo.sunshine.dto.UserDto;
import com.tianhuo.thkernel.domain.user.User;
import com.tianhuo.thkernel.port.persistence.dao.mysql.UserMapper;
import com.tianhuo.thcommon.utils.StringUtil;
import com.tianhuo.thkernel.port.persistence.entity.UserDO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * user repository
 * @author liguowei
 * @date 2019-12-03 16:51:49
 */
@Repository
public class UserRepository {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private RedisTemplate<String, User> userRedisTemplate;

  private static final long ONE_HOUR = 1000 * 60 * 60;

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
    User user = userRedisTemplate.opsForValue().get(cacheIdKey(id));
    if(null != user && Objects.equals(id, user.getId())) {
      return user;
    }
    user = UserConverter.convert(
        userMapper.findById(StringUtil.convertToLong(id, 0))
    );
    if(null != user) {
      userRedisTemplate.opsForValue().set(cacheIdKey(id), user, timeout(), TimeUnit.MILLISECONDS);
    }
    return user;
  }

  /**
   * query user by username
   * @param username username
   * @return user domain object
   */
  public User queryByUsername(String username) {
    User user = userRedisTemplate.opsForValue().get(cacheUsernameKey(username));
    if(null != user && Objects.equals(user.getUsername(), username)) {
      return user;
    }
    user = UserConverter.convert(userMapper.findByUsername(username));
    if(user != null) {
      userRedisTemplate.opsForValue().set(cacheUsernameKey(username), user, timeout(), TimeUnit.MILLISECONDS);
    }
    return user;
  }

  /**
   * update one user
   * @param user user domain object to update
   */
  public void update(User user) {
    userMapper.update(UserConverter.convert(user));
    userRedisTemplate.delete(cacheIdKey(user.getId()));
    userRedisTemplate.delete(cacheUsernameKey(user.getUsername()));
  }

  /**
   * mark delete by user id
   * @param id user id
   */
  public void delete(String id) {
    userRedisTemplate.delete(cacheIdKey(id));
    UserDO user = userMapper.findById(StringUtil.convertToLong(id, 0L));
    if(null == user) {
      return;
    }
    userRedisTemplate.delete(cacheUsernameKey(user.getUsername()));
    userMapper.delete(StringUtil.convertToLong(id, 0));
  }

  /**
   * get the string key in redis cache by id
   * @param id id
   * @return key
   */
  private String cacheIdKey(String id) {
    return id + "_id_th_u";
  }

  /**
   * get the string key in redis cache by username
   * @param username username
   * @return key
   */
  private String cacheUsernameKey(String username) {
    return username + "_username_th_u";
  }

  private Long timeout() {
    return (long) (ONE_HOUR * 12 + ONE_HOUR * 12 * Math.random());
  }
}
