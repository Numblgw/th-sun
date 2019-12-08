package com.tianhuo.thkernel.domain.category;

import com.tianhuo.thcommon.utils.CheckUtil;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * category entity
 * @author liguowei
 * @date 2019-11-23 18:17:17
 */
@Getter
@EqualsAndHashCode
@ToString
public class Category {

  /**
   * category id
   */
  private Integer id;

  /**
   * category name
   */
  private String name;

  public Category(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  private void check() {
    CheckUtil.notNull(this.id, "category id is null");
    CheckUtil.notNull(this.name, "category name is null");
  }
}
