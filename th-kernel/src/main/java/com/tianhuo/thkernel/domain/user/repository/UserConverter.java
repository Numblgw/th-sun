package com.tianhuo.thkernel.domain.user.repository;

import com.alibaba.druid.util.StringUtils;
import com.tianhuo.sunshine.dto.UserDto;
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
public class UserConverter {

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
  public static User convert(UserCacheDO userCache) {
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
  public static UserCacheDO toUserCache(User user) {
    return UserCacheDO.builder()
        .id(user.getId())
        .username(user.getUsername())
        .nickname(user.getNickname())
        .roleId(user.getRoleId())
        .registeredTime(user.getRegisteredTime())
        .build();
  }

  /**
   * user domain object convert to user data transfer object
   * @param user user domain object
   * @return user data transfer object
   */
  public static UserDto toUserDTO(User user) {
    if(user == null) {
      return null;
    }
    return UserDto.builder()
        .id(user.getId())
        .username(user.getUsername())
        .password(user.getPassword())
        .nickname(user.getNickname())
        .registeredTime(user.getRegisteredTime())
        .roleId(user.getRoleId())
        .build();
  }

  public static UserDto toUserDTO(User user, String sessionId, Long sessionLifeTime) {
    if(user == null || StringUtils.isEmpty(sessionId) || sessionLifeTime <= 0) {
      return null;
    }
    return UserDto.builder()
        .id(user.getId())
        .sessionId(sessionId)
        .sessionLifeTime(sessionLifeTime)
        .username(user.getUsername())
        .nickname(user.getNickname())
        .registeredTime(user.getRegisteredTime())
        .roleId(user.getRoleId())
        .build();
  }

  /**
   * convert UserDto --> User
   * @param userDto user dto
   * @return user domain object
   */
  public static User convert(UserDto userDto) {
    if(userDto == null) {
      return null;
    }
    return new User(
        userDto.getId(),
        userDto.getUsername(),
        userDto.getPassword(),
        userDto.getNickname(),
        userDto.getRegisteredTime(),
        userDto.getRoleId()
    );
  }
}
