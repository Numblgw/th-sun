package com.tianhuo.thkernel.domain.article;

import com.tianhuo.thcommon.utils.CheckUtil;
import com.tianhuo.thkernel.domain.category.Category;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/**
 * article domain object
 * @author liguowei
 * @date 2019-11-23 18:01:56
 */
@Getter
@ToString
@EqualsAndHashCode
public class Article {

  /**
   * article excerpt length
   */
  private static final int ARTICLE_EXCERPT_LENGTH = 20;

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
  private Category category;

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

  public Article(String id, String senderId, String title, Category category, List<String> tags, String detail, LocalDateTime createAt, LocalDateTime modifyAt) {
    this.id = id;
    this.senderId = senderId;
    this.title = title;
    this.category = category;
    this.tags = tags;
    this.detail = detail;
    this.createAt = createAt;
    this.modifyAt = modifyAt;
    check();
  }

  /**
   * get article excerpt
   * @return article excerpt
   */
  public String getExcerpt() {
    if(this.detail.length() < ARTICLE_EXCERPT_LENGTH) {
      return this.detail;
    }
    return this.detail.substring(0, ARTICLE_EXCERPT_LENGTH);
  }

  /**
   * check this object
   */
  private void check() {
    CheckUtil.notNull(this.id, "article id is null");
    CheckUtil.notNull(this.senderId, "article sender id is null");
    CheckUtil.notNull(this.title, "article title is null");
    CheckUtil.notNull(this.category, "article category is null");
    CheckUtil.notNull(this.createAt, "article create date is null");
  }
}

