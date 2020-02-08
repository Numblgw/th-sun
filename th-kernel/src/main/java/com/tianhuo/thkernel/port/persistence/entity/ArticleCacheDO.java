package com.tianhuo.thkernel.port.persistence.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * article 缓存实体
 * @author liguowei
 * @date 2020-02-08 14:50:21
 */
@Data
@Builder
public class ArticleCacheDO {

  /**
   * id
   */
  private String id;

  /**
   * author id
   */
  private String senderId;

  /**
   * title
   */
  private String title;

  /**
   * category
   */
  private String categoryId;

  /**
   * tag list
   */
  private List<String> tags;

  /**
   * detail
   */
  private String detail;

  /**
   * created date
   */
  private LocalDateTime createAt;

  /**
   * modification date
   */
  private LocalDateTime modifyAt;

}
