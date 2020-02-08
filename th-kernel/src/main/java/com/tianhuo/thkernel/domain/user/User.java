package com.tianhuo.thkernel.domain.user;

import com.tianhuo.sunshine.exception.UserUpdateFailException;
import com.tianhuo.thcommon.utils.CheckUtil;
import com.tianhuo.thkernel.application.cmd.UserUpdateCmd;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Objects;

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

  /**
   * update this entity
   * @param cmd update command
   */
  public void update(UserUpdateCmd cmd) {
    if(null == cmd) {
      throw new UserUpdateFailException("the cmd is null");
    }
    if(!Objects.equals(this.id, cmd.getId())) {
      throw new UserUpdateFailException("user.id != cmd.id");
    }
    if(!StringUtils.isEmpty(cmd.getPassword())) {
      this.password = cmd.getPassword();
    }
    if(!StringUtils.isEmpty(cmd.getNickname())) {
      this.nickname = cmd.getNickname();
    }
    if(!StringUtils.isEmpty(cmd.getRoleId())) {
      this.roleId = cmd.getRoleId();
    }
  }

  /**
   * check this entity complete
   */
  private void check() {
    CheckUtil.notNull(this.username, "username is null");
    CheckUtil.notNull(this.registeredTime, "user registered time is null");
    CheckUtil.notNull(this.roleId, "user role id is null");
  }
}
