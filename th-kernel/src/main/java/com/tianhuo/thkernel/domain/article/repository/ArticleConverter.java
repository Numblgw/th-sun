package com.tianhuo.thkernel.domain.article.repository;

import com.google.common.collect.Lists;
import com.tianhuo.thkernel.domain.article.Article;
import com.tianhuo.thkernel.port.persistence.entity.ArticleDetailDO;
import com.tianhuo.thkernel.port.persistence.entity.ArticleExcerptDO;
import com.tianhuo.thcommon.utils.StringUtil;


/**
 * converter
 * Article <----> ArticleDetailDO, ArticleExcerptDO
 * @author liguowei
 * @date 2019-11-24 11:14:43
 */
class ArticleConverter {

  /**
   * assemble article
   * @param articleExcerpt article excerpt
   * @param articleDetail article detail
   * @return article domain object
   */
  static Article assemble(ArticleExcerptDO articleExcerpt, ArticleDetailDO articleDetail) {
    return new Article(
        String.valueOf(articleExcerpt.getId()),
        String.valueOf(articleExcerpt.getSenderId()),
        articleExcerpt.getTitle(),
        String.valueOf(articleExcerpt.getCategoryId()),
        Article.getTagListByString(articleExcerpt.getTags()),
        articleDetail.getDetail(),
        articleExcerpt.getCreateAt(),
        articleExcerpt.getModifyAt()
    );
  }

  /**
   * convert to article excerpt object
   * @param article article object
   * @return article excerpt object
   */
  static ArticleExcerptDO convertToArticleExcerpt(Article article) {
    ArticleExcerptDO articleExcerpt = new ArticleExcerptDO();
    articleExcerpt.setId(StringUtil.convertToLong(article.getId(), 0L));
    articleExcerpt.setSenderId(StringUtil.convertToLong(article.getSenderId(), 0L));
    articleExcerpt.setTitle(article.getTitle());
    articleExcerpt.setCategoryId(StringUtil.convertToLong(article.getCategoryId(), 0L).intValue());
    articleExcerpt.setTags(article.getTagsString());
    articleExcerpt.setExcerpt(article.getExcerpt());
    articleExcerpt.setCreateAt(article.getCreateAt());
    articleExcerpt.setModifyAt(article.getModifyAt());
    return articleExcerpt;
  }

  /**
   * convert to article detail object
   * @param article article object
   * @return article detail object
   */
  static ArticleDetailDO convertToArticleDetail(Article article) {
    ArticleDetailDO articleDetail = new ArticleDetailDO();
    articleDetail.setId(article.getId());
    articleDetail.setDetail(article.getDetail());
    return articleDetail;
  }

  /**
   * ArticleExcerpt --> Article
   * ArticleExcerpt.excerpt --> Article.detail
   * @param articleExcerpt article excerpt data object
   * @return article object
   */
  static Article convertToArticleBy(ArticleExcerptDO articleExcerpt) {
    return new Article(
        String.valueOf(articleExcerpt.getId()),
        String.valueOf(articleExcerpt.getSenderId()),
        articleExcerpt.getTitle(),
        String.valueOf(articleExcerpt.getCategoryId()),
        Lists.newArrayList(articleExcerpt.getTags().split(",")),
        articleExcerpt.getExcerpt(),
        articleExcerpt.getCreateAt(),
        articleExcerpt.getModifyAt()
    );
  }
}
