package com.tianhuo.sunshine.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * role dto
 * @author liguowei
 * @date 2020-02-18 20:05:50
 */
@Data
@Builder
public class RoleDto implements Serializable {

  private static final long serialVersionUID = 2441738259730060183L;
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
