package com.tianhuo.thkernel.domain.user.repository;

import com.tianhuo.thkernel.domain.user.User;
import com.tianhuo.thkernel.port.persistence.entity.UserCacheDO;
import com.tianhuo.thkernel.port.persistence.entity.UserDO;
import com.tianhuo.thcommon.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 *      convert
 * User <---> UserDO
 * @author liguowei
 * @date 2019-12-03 16:54:01
 */
class UserConverter {

  /**
   * UserDO to User
   * @param userEntity user data object
   * @return user domain object
   */
  static User convert(UserDO userEntity) {
    if(null == userEntity) {
      return null;
    }
    return new User(
        String.valueOf(userEntity.getId()),
        userEntity.getUsername(),
        userEntity.getPassword(),
        userEntity.getNickname(),
        userEntity.getRegisteredTime(),
        String.valueOf(userEntity.getRoleId())
    );
  }

  static List<User> convert(Collection<UserDO> users) {
    return new ArrayList<>(users).stream()
        .map(UserConverter::convert)
        .collect(Collectors.toList());
  }

  /**
   * User to UserDO
   * @param user user domain object
   * @return user data object
   */
  static UserDO toUserDO(User user) {
    UserDO userEntity = new UserDO();
    userEntity.setId(StringUtil.convertToLong(user.getId(), 0));
    userEntity.setUsername(user.getUsername());
    userEntity.setPassword(user.getPassword());
    userEntity.setNickname(user.getNickname());
    userEntity.setRegisteredTime(user.getRegisteredTime());
    userEntity.setRoleId(StringUtil.convertToLong(user.getRoleId(), 0L).intValue());
    return userEntity;
  }

  /**
   * user cache do -> user domain object
   * @param userCache user cache do
   * @return user domain object
   */
  static User convert(UserCacheDO userCache) {
    if(null == userCache) {
      return null;
    }
    return new User(
        userCache.getId(),
        userCache.getUsername(),
        null,
        userCache.getNickname(),
        userCache.getRegisteredTime(),
        userCache.getRoleId()
        );
  }

  /**
   * user domain object -> user cache do
   * @param user user domain object
   * @return user cache do
   */
  static UserCacheDO toUserCache(User user) {
    return UserCacheDO.builder()
        .id(user.getId())
        .username(user.getUsername())
        .nickname(user.getNickname())
        .roleId(user.getRoleId())
        .registeredTime(user.getRegisteredTime())
        .build();
  }
}
