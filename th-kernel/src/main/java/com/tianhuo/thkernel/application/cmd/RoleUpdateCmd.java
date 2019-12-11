package com.tianhuo.thkernel.application.cmd;

import lombok.Builder;
import lombok.Data;

/**
 * update role command
 * @author liguowei
 * @date 2019-12-09 22:38:20
 */
@Data
@Builder
public class RoleUpdateCmd {

  private String id;

  private String name;

  private String description;

}
