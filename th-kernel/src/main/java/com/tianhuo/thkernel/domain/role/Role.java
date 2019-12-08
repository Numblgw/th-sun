package com.tianhuo.thkernel.domain.role;


import com.tianhuo.thcommon.utils.CheckUtil;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * user role
 * @author liguowei
 * @date 2019-12-02 19:54:39
 */
@Getter
@EqualsAndHashCode
@ToString
public class Role {

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

  public Role(String id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
    check();
  }

  /**
   * 检查完整性
   */
  private void check() {
    CheckUtil.notNull(id, "role id is null");
    CheckUtil.notNull(name, "role name is null");
  }
}
