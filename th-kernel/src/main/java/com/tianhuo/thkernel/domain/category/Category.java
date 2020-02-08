package com.tianhuo.thkernel.domain.category;

import com.tianhuo.sunshine.exception.CategoryUpdateFailException;
import com.tianhuo.thcommon.utils.CheckUtil;
import com.tianhuo.thkernel.application.cmd.CategoryUpdateCmd;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import org.springframework.util.StringUtils;

import java.util.Objects;

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

  /**
   * update this category
   * @param cmd update command
   */
  public void update(CategoryUpdateCmd cmd) {
    if(null == cmd) {
      throw new CategoryUpdateFailException("category update cmd is null");
    }
    if(!Objects.equals(this.id, cmd.getId())) {
      throw new CategoryUpdateFailException("category id != cmd id");
    }
    if(!StringUtils.isEmpty(cmd.getName())) {
      this.name = cmd.getName();
    }
  }

  /**
   * check this entity complete
   */
  private void check() {
    CheckUtil.notNull(this.name, "category name is null");
  }
}
