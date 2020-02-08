package com.tianhuo.thkernel.application.cmd;

import lombok.Builder;
import lombok.Data;

/**
 * @author liguowei
 * @date 2019-12-11 18:10:58
 */
@Data
@Builder
public class UserUpdateCmd {

  private String id;

  private String password;

  private String nickname;

  private String roleId;
}
