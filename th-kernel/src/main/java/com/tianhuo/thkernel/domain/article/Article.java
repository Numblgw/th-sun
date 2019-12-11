package com.tianhuo.thkernel.domain.article;

import com.google.common.collect.Lists;
import com.tianhuo.sunshine.exception.ArticleUpdateFailException;
import com.tianhuo.thcommon.utils.CheckUtil;
import com.tianhuo.thkernel.application.cmd.ArticleUpdateCmd;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
   * string tag split by
   */
  private static final String TAG_SPLIT = ",";

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

  public Article(String id, String senderId, String title, String categoryId, List<String> tags, String detail, LocalDateTime createAt, LocalDateTime modifyAt) {
    this.id = id;
    this.senderId = senderId;
    this.title = title;
    this.categoryId = categoryId;
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
   * add article tag
   */
  public void addTag(String tag) {
    if(null == this.tags) {
      this.tags = new ArrayList<>();
    }
    tags.add(tag);
  }

  /**
   * get tags string split by
   * @return tag string
   */
  public String getTagsString() {
    StringBuilder stringBuilder = new StringBuilder();
    for(int i = 0 ; i < this.tags.size() ; i++) {
      stringBuilder.append(this.tags.get(i));
      if(i != this.tags.size() - 1) {
        stringBuilder.append(TAG_SPLIT);
      }
    }
    return stringBuilder.toString();
  }

  /**
   * get tag list by string tag
   * @param tags tags
   * @return tag list
   */
  public static List<String> getTagListByString(String tags) {
    if(StringUtils.isEmpty(tags)) {
      return Collections.emptyList();
    }
    return Lists.newArrayList(tags.split(TAG_SPLIT));
  }

  /**
   * update this article
   * @param cmd article update command
   */
  public void update(ArticleUpdateCmd cmd) {
    if(null == cmd) {
      throw new ArticleUpdateFailException("update cmd is null!");
    }
    if(!Objects.equals(this.id, cmd.getId())
        || !Objects.equals(this.senderId, cmd.getSenderId())) {
      throw new ArticleUpdateFailException("id and sender id check fail, article.id != cmd.id or article.senderId != cmd.senderId");
    }
    if(!StringUtils.isEmpty(cmd.getTitle())) {
      this.title = cmd.getTitle();
    }
    if(!StringUtils.isEmpty(cmd.getCategoryId())) {
      this.categoryId = cmd.getCategoryId();
    }
    if(!CollectionUtils.isEmpty(cmd.getTags())) {
      this.tags = cmd.getTags();
    }
    if(!StringUtils.isEmpty(cmd.getDetail())) {
      this.detail = cmd.getDetail();
    }
    this.modifyAt = LocalDateTime.now();
  }

  /**
   * check this object
   */
  private void check() {
    CheckUtil.notNull(this.id, "article id is null");
    CheckUtil.notNull(this.senderId, "article sender id is null");
    CheckUtil.notNull(this.title, "article title is null");
    CheckUtil.notNull(this.categoryId, "article category id is null");
    CheckUtil.notNull(this.createAt, "article create date is null");
  }
}

