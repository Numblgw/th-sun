package com.tianhuo.sunshine.dto;

import com.tianhuo.sunshine.enums.UserOperateResult;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * user data transfer object
 * @author liguowei
 * @date 2019-12-04 20:10:44
 */
@Data
@Builder
public class UserDto implements Serializable {

  private static final long serialVersionUID = -5604108995178646863L;

  private UserOperateResult userOperateResult;

  private String id;

  private String username;

  private String password;

  private String nickname;

  private LocalDateTime registeredTime;

  private String roleId;

  private Integer articleCount;

  private LocalDateTime lastPublishDate;
}
