package com.tianhuo.thkernel.domain.article.repository;

import com.tianhuo.sunshine.exception.ArticleDetailNotFoundException;
import com.tianhuo.thcommon.utils.StringUtil;
import com.tianhuo.thkernel.domain.article.Article;
import com.tianhuo.thkernel.port.persistence.dao.mongo.ArticleDetailDao;
import com.tianhuo.thkernel.port.persistence.dao.mysql.ArticleExcerptMapper;
import com.tianhuo.thkernel.port.persistence.entity.ArticleCacheDO;
import com.tianhuo.thkernel.port.persistence.entity.ArticleDetailDO;
import com.tianhuo.thkernel.port.persistence.entity.ArticleExcerptDO;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * article repository
 * @author liguowei
 * @date 2019-11-23 22:49:22
 */
@Repository
public class ArticleRepository {

  @Resource
  private ArticleDetailDao articleDetailDao;

  @Resource
  private ArticleExcerptMapper articleExcerptMapper;

  @Resource
  private RedisTemplate<String, ArticleCacheDO> articleRedisTemplate;

  /**
   * query article by id
   * @param id id
   * @return article
   */
  public Article queryById(Long id) {
    // 先取缓存
    ArticleCacheDO articleCache = articleRedisTemplate.opsForValue().get(singleCacheKey(String.valueOf(id)));
    if(null != articleCache) {
      return ArticleConverter.convertToArticleByCache(articleCache);
    }

    ArticleExcerptDO articleExcerpt = articleExcerptMapper.queryById(id);
    if(null == articleExcerpt) {
      return null;
    }
    ArticleDetailDO articleDetail = articleDetailDao.queryById(String.valueOf(id));
    if(null == articleDetail) {
      throw new ArticleDetailNotFoundException(String.valueOf(id));
    }
    Article article = ArticleConverter.assemble(articleExcerpt, articleDetail);
    articleRedisTemplate.opsForValue()
        .set(singleCacheKey(String.valueOf(id)), ArticleConverter.toArticleCache(article),
            timeout(), TimeUnit.MILLISECONDS);
    return article;
  }

  /**
   * query article excerpt by batch
   * @param start start article id
   * @param limit batch size
   * @return article list
   */
  public List<Article> queryExcerptByBatch(Long start, Integer limit) {
    return articleExcerptMapper.queryByBatch(start, limit).stream()
        .map(ArticleConverter::convertToArticleBy)
        .collect(Collectors.toList());
  }

  /**
   * save article
   * @param article article domain object
   */
  @Transactional(rollbackFor = RuntimeException.class)
  public void save(Article article) {
    ArticleExcerptDO articleExcerptDO = ArticleConverter.convertToArticleExcerpt(article);
    articleExcerptMapper.insert(articleExcerptDO);
    article.insertId(String.valueOf(articleExcerptDO.getId()));
    articleDetailDao.insert(ArticleConverter.convertToArticleDetail(article));
    // 考虑到用户发布之后会马上去查看，所以发布之后存到缓存里
    articleRedisTemplate.opsForValue()
        .set(singleCacheKey(article.getId()), ArticleConverter.toArticleCache(article),
            tempTimeout(), TimeUnit.MILLISECONDS);
  }

  /**
   * modify article
   * @param article modified article
   */
  @Transactional(rollbackFor = RuntimeException.class)
  public void modify(Article article) {
    articleRedisTemplate.delete(singleCacheKey(article.getId()));
    articleExcerptMapper.update(ArticleConverter.convertToArticleExcerpt(article));
    articleDetailDao.update(ArticleConverter.convertToArticleDetail(article));
  }

  /**
   * delete article
   * @param id article id
   */
  @Transactional(rollbackFor = RuntimeException.class)
  public void delete(Long id) {
    articleRedisTemplate.delete(singleCacheKey(String.valueOf(id)));
    articleExcerptMapper.delete(id);
    articleDetailDao.delete(String.valueOf(id));
  }

  /**
   * count article by user id
   * @param userId user id
   * @return article count
   */
  public Integer countByUser(Long userId) {
    return articleExcerptMapper.countByUser(userId);
  }

  /**
   * get last publishing date
   * @param userId user id
   * @return last publishing date
   */
  public LocalDateTime getLastPublishingDate(Long userId) {
    return articleExcerptMapper.getLastPublishingDate(userId);
  }

  /**
   * query article by category id
   * @param categoryId category id
   * @return list of article
   */
  public List<Article> queryByCategoryId(String categoryId, Long start, Integer limit) {
    List<ArticleExcerptDO> articles = articleExcerptMapper.queryByCategoryId(
            StringUtil.convertToLong(categoryId, 0L).intValue(), start, limit);
    return articles.stream()
        .map(ArticleConverter::convertToArticleBy)
        .collect(Collectors.toList());
  }

  /**
   * 生成单个实体缓存的 key
   * @param id id
   * @return key in cache
   */
  private String singleCacheKey(String id) {
    if(null == id) {
      throw new NullPointerException("id is null");
    }
    return id + "tianhuo_article";
  }

  /**
   * get cache time out
   * @return millisecond
   */
  private Long timeout() {
    long sixHour = 1000 * 60 * 60 * 6;
    return sixHour + (long) (Math.random() * sixHour);
  }

  /**
   * get temp cache time out
   * @return time out
   */
  private Long tempTimeout() {
    return (long) 1000 * 5;
  }
}
