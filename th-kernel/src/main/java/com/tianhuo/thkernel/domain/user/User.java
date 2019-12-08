package com.tianhuo.thkernel.domain.user;

import com.tianhuo.thcommon.utils.CheckUtil;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * user domain object
 * @author liguowei
 * @date 2019-12-02 19:52:58
 */
@Getter
@ToString
@EqualsAndHashCode
public class User {

  private String id;

  private String username;

  private String password;

  private String nickname;

  private LocalDateTime registeredTime;

  private String roleId;

  public User(String id, String username, String password, String nickname,
      LocalDateTime registeredTime, String roleId) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.nickname = nickname;
    this.registeredTime = registeredTime;
    this.roleId = roleId;
    check();
  }

  private void check() {
    CheckUtil.notNull(this.id, "user id is null");
    CheckUtil.notNull(this.username, "username is null");
    CheckUtil.notNull(this.registeredTime, "user registered time is null");
    CheckUtil.notNull(this.roleId, "user role id is null");
  }
}
