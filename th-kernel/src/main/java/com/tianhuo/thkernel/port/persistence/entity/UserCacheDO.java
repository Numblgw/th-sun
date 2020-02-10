package com.tianhuo.thkernel.port.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * user 缓存实体
 * @author liguowei
 * @date 2020-02-08 14:50:43
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCacheDO {

  private String id;

  private String username;

  private String nickname;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "GMT+8")
  private LocalDateTime registeredTime;

  private String roleId;
}
