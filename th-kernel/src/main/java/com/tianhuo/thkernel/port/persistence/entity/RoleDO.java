package com.tianhuo.thkernel.port.persistence.entity;

import lombok.Data;

/**
 * user role data object
 * @author liguowei
 * @date 2019-12-03 17:16:25
 */
@Data
public class RoleDO {

  /**
   * role id
   */
  private Integer id;

  /**
   * role name
   */
  private String name;

  /**
   * role description
   */
  private String description;
}
