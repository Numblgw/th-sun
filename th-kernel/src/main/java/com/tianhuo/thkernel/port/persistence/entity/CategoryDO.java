package com.tianhuo.thkernel.port.persistence.entity;

import lombok.Data;

/**
 * category data object
 * @author liguowei
 * @date 2019-12-02 20:54:26
 */
@Data
public class CategoryDO {

  /**
   * id
   */
  private Integer id;

  /**
   * category name
   */
  private String name;
}
