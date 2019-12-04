package com.tianhuo.thkernel.port.persistence.entity;

import lombok.Data;

/**
 * article article data object
 * @author liguowei
 * @date 2019-11-23 19:17:55
 */
@Data
public class ArticleDetailDO {

  /**
   * article id
   */
  private String id;

  /**
   * article detail
   */
  private String detail;
}
