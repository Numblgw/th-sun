package com.tianhuo.thkernel.domain.user.repository;

import com.tianhuo.thkernel.domain.user.User;
import com.tianhuo.thkernel.port.persistence.dao.mysql.UserMapper;
import com.tianhuo.thcommon.utils.StringUtil;
import com.tianhuo.thkernel.port.persistence.entity.UserCacheDO;
import com.tianhuo.thkernel.port.persistence.entity.UserDO;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * user repository
 * @author liguowei
 * @date 2019-12-03 16:51:49
 */
@Repository
public class UserRepository {

  @Resource
  private UserMapper userMapper;

  @Resource
  private RedisTemplate<String, UserCacheDO> userRedisTemplate;

  private static final long ONE_HOUR = 1000 * 60 * 60;

  /**
   * add one user
   * @param user user to save
   * @return if success return user id
   */
  public Long add(User user) {
    UserDO userToInsert = UserConverter.toUserDO(user);
    if(userMapper.insert(userToInsert) == 1) {
      userRedisTemplate.opsForValue()
          .set(cacheIdKey(String.valueOf(userToInsert.getId())), UserConverter.toUserCache(user), timeout(), TimeUnit.MILLISECONDS);
      user.setIdIfAbsent(String.valueOf(userToInsert.getId()));
    }
    return userToInsert.getId();
  }

  /**
   * query user info by user id
   * @param id user id
   * @return user domain object
   */
  public User queryById(String id) {
    UserCacheDO userCache = userRedisTemplate.opsForValue().get(cacheIdKey(id));
    if(null != userCache && Objects.equals(id, userCache.getId())) {
      return UserConverter.convert(userCache);
    }
    User user = UserConverter.convert(
        userMapper.findById(StringUtil.convertToLong(id, 0))
    );
    if(null != user) {
      userRedisTemplate.opsForValue().set(cacheIdKey(id), UserConverter.toUserCache(user), timeout(), TimeUnit.MILLISECONDS);
    }
    return user;
  }

  /**
   * query user by username
   * @param username username
   * @return user domain object
   */
  public User queryByUsername(String username) {
    User user = UserConverter.convert(userMapper.findByUsername(username));
    if(user != null) {
      userRedisTemplate.opsForValue().set(cacheIdKey(user.getId()), UserConverter.toUserCache(user),
          timeout(), TimeUnit.MILLISECONDS);
    }
    return user;
  }

  /**
   * update one user
   * @param user user domain object to update
   */
  public void update(User user) {
    userMapper.update(UserConverter.toUserDO(user));
    userRedisTemplate.delete(cacheIdKey(user.getId()));
  }

  /**
   * mark delete by user id
   * @param id user id
   */
  public void delete(String id) {
    UserDO user = userMapper.findById(StringUtil.convertToLong(id, 0L));
    if(null == user) {
      return;
    }
    userRedisTemplate.delete(cacheIdKey(id));
    userMapper.delete(StringUtil.convertToLong(id, 0));
  }

  /**
   * query users by ids
   * @param ids collection of user id
   * @return list of user
   */
  public List<User> queryUsers(Collection<String> ids) {
    List<User> res = new ArrayList<>(ids.size());
    List<String> idList = new ArrayList<>(ids).stream()
        .distinct()
        .collect(Collectors.toList());
    List<User> hitCache = idList.stream()
        .map(id -> UserConverter.convert(userRedisTemplate.opsForValue().get(cacheIdKey(id))))
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
    res.addAll(hitCache);
    if(Objects.equals(hitCache.size(), idList.size())) {
      return res;
    }
    // 删掉已经命中缓存的 id
    hitCache.stream().map(User::getId).forEach(idList::remove);
    List<Integer> param = new ArrayList<>(idList).stream()
        .map(Integer::new)
        .collect(Collectors.toList());
    List<User> unCache = UserConverter.convert(userMapper.queryUserByIds(param));
    // 将为缓存的加入缓存
    for (User user : unCache) {
      userRedisTemplate.opsForValue()
          .set(cacheIdKey(user.getId()), UserConverter.toUserCache(user), timeout(), TimeUnit.MILLISECONDS);
    }
    res.addAll(unCache);
    return res;
  }

  /**
   * query user list
   * @return list of user
   */
  public List<User> userList() {
    return UserConverter.convert(userMapper.userList());
  }

  /**
   * modify user role
   * @param uid uid
   * @param roleId role id
   * @return success true
   */
  public boolean modifyUserRoleId(String uid, String roleId) {
    return userMapper.modifyUserRole(
        StringUtil.convertToLong(uid, 0L),
        StringUtil.convertToLong(roleId, 0L).intValue()
    ) > 0;
  }

  /**
   * get the string key in redis cache by id
   * @param id id
   * @return key
   */
  private String cacheIdKey(String id) {
    return id + "_id_th_u";
  }

  private Long timeout() {
    return (long) (ONE_HOUR * 12 + ONE_HOUR * 12 * Math.random());
  }
}
