package com.tianhuo.thkernel.port.persistence.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * user 缓存实体
 * @author liguowei
 * @date 2020-02-08 14:50:43
 */
@Data
@Builder
public class UserCacheDO {

  private String id;

  private String username;

  private String nickname;

  private LocalDateTime registeredTime;

  private String roleId;
}
