package com.tianhuo.sunshine.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * user data transfer object
 * @author liguowei
 * @date 2019-12-04 20:10:44
 */
@Data
@Builder
public class UserDto {

  private String id;

  private String username;

  private String password;

  private String nickname;

  private LocalDateTime registeredTime;

  private String roleId;

  private Integer articleCount;

  private LocalDateTime lastPublishDate;
}
