package com.tianhuo.thkernel.domain.user.repository;

import com.tianhuo.thkernel.domain.user.User;
import com.tianhuo.thkernel.port.persistence.entity.UserDO;
import com.tianhuo.thcommon.utils.StringUtil;

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
        userEntity.getRoleId()
    );
  }

  /**
   * User to UserDO
   * @param user user domain object
   * @return user data object
   */
  static UserDO convert(User user) {
    UserDO userEntity = new UserDO();
    userEntity.setId(StringUtil.convertToLong(user.getId(), 0));
    userEntity.setUsername(user.getUsername());
    userEntity.setPassword(user.getPassword());
    userEntity.setNickname(user.getNickname());
    userEntity.setRegisteredTime(user.getRegisteredTime());
    userEntity.setRoleId(user.getRoleId());
    return userEntity;
  }
}
