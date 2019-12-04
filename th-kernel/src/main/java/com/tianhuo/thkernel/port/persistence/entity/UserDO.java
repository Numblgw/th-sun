package com.tianhuo.thkernel.port.persistence.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * user data object
 * @author liguowei
 * @date 2019-12-03 16:23:05
 */
@Data
public class UserDO {

  private Long id;

  private String username;

  private String password;

  private String nickname;

  private LocalDateTime registeredTime;

  private String roleId;
}
