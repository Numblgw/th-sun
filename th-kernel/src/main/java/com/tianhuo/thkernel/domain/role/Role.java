package com.tianhuo.thkernel.domain.role;


import com.tianhuo.sunshine.exception.RoleUpdateFailException;
import com.tianhuo.thkernel.application.cmd.RoleUpdateCmd;
import com.tianhuo.thcommon.utils.CheckUtil;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import org.springframework.util.StringUtils;

import java.util.Objects;

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
   * update this role entity
   * @param cmd update command
   */
  public void update(RoleUpdateCmd cmd) {
    if(null == cmd) {
      throw new RoleUpdateFailException("update cmd is null");
    }
    if(!Objects.equals(this.id, cmd.getId())) {
      throw new RoleUpdateFailException("role.id != cmd.id");
    }
    if(!StringUtils.isEmpty(cmd.getName())) {
      this.name = cmd.getName();
    }
    if(!StringUtils.isEmpty(cmd.getDescription())) {
      this.description = cmd.getDescription();
    }
  }

  /**
   * check this entity complete
   */
  private void check() {
    CheckUtil.notNull(id, "role id is null");
    CheckUtil.notNull(name, "role name is null");
  }
}
