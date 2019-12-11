package com.tianhuo.thkernel.application.cmd;

import lombok.Builder;
import lombok.Data;

/**
 * update category command
 * @author liguowei
 * @date 2019-12-09 22:28:46
 */
@Data
@Builder
public class CategoryUpdateCmd {

  private Integer id;

  private String name;
}
