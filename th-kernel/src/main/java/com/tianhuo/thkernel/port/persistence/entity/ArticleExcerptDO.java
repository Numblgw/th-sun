package com.tianhuo.thkernel.port.persistence.entity;


import lombok.Data;

import java.time.LocalDateTime;

/**
 * article base data object
 * @author liguowei
 * @date 2019-11-23 19:15:47
 */
@Data
public class ArticleExcerptDO {

  /**
   * id
   */
  private Long id;

  /**
   * sender id
   */
  private Long senderId;

  /**
   * title
   */
  private String title;

  /**
   * category id
   */
  private Integer categoryId;

  /**
   * tags split by ','
   */
  private String tags;

  /**
   * article excerpt
   */
  private String excerpt;

  /**
   * created date
   */
  private LocalDateTime createAt;

  /**
   * modification date
   */
  private LocalDateTime modifyAt;
}
