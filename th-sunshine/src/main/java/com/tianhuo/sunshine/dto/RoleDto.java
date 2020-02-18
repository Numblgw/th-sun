package com.tianhuo.sunshine.dto;

import lombok.Builder;
import lombok.Data;

/**
 * role dto
 * @author liguowei
 * @date 2020-02-18 20:05:50
 */
@Data
@Builder
public class RoleDto {

  /**
   * role id
   */
  private String id;

  /**
   * role name
   */
  private String name;

  /**
   * role description
   */
  private String description;
}
