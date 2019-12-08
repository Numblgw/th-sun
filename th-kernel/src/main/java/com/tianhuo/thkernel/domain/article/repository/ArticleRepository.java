package com.tianhuo.thkernel.domain.article.repository;

import com.tianhuo.sunshine.exception.ArticleDetailNotFoundException;
import com.tianhuo.thkernel.domain.article.Article;
import com.tianhuo.thkernel.port.persistence.dao.mongo.ArticleDetailDao;
import com.tianhuo.thkernel.port.persistence.dao.mysql.ArticleExcerptMapper;
import com.tianhuo.thkernel.port.persistence.entity.ArticleDetailDO;
import com.tianhuo.thkernel.port.persistence.entity.ArticleExcerptDO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

  @Autowired
  private ArticleDetailDao articleDetailDao;

  @Autowired
  private ArticleExcerptMapper articleExcerptMapper;

  @Autowired
  private RedisTemplate<String, Article> articleRedisTemplate;

  /**
   * query article by id
   * @param id id
   * @return article
   */
  public Article queryById(Long id) {
    // 先取缓存
    Article article = articleRedisTemplate.opsForValue().get(singleCacheKey(id));
    if(null != article) {
      return article;
    }

    ArticleExcerptDO articleExcerpt = articleExcerptMapper.queryById(id);
    if(null == articleExcerpt) {
      return null;
    }
    ArticleDetailDO articleDetail = articleDetailDao.queryById(String.valueOf(id));
    if(null == articleDetail) {
      throw new ArticleDetailNotFoundException(String.valueOf(id));
    }
    article = ArticleConverter.assemble(articleExcerpt, articleDetail);
    articleRedisTemplate.opsForValue()
        .set(singleCacheKey(id), article, timeout(), TimeUnit.MILLISECONDS);
    return article;
  }

  /**
   * query article excerpt by batch
   * @param start start article id
   * @param limit batch size
   * @return article list
   */
  public List<Article> queryByBatch(Long start, Integer limit) {
    List<Article> result = new ArrayList<>(limit);
    List<Long> needQueryIds = new ArrayList<>(limit);
    // 先查缓存
    for(long i = start ; i <= start + limit ; i++) {
      Article tmp = articleRedisTemplate.opsForValue().get(singleCacheKey(i));
      if(null == tmp) {
        needQueryIds.add(i);
      }else {
        result.add(tmp);
      }
    }
    if(CollectionUtils.isEmpty(needQueryIds)) {
      return result;
    }
    // 查库
    result.addAll(
        articleExcerptMapper.queryByIds(needQueryIds).stream()
            .map(ArticleConverter::convertToArticleBy)
            .collect(Collectors.toList())
    );
    // 将结果缓存
    for (Article article : result) {
      articleRedisTemplate.opsForValue()
          .set(batchCacheKey(Long.valueOf(article.getId())), article, timeout(), TimeUnit.MILLISECONDS);
    }
    return result;
  }

  /**
   * save article
   * @param article article domain object
   */
  @Transactional(rollbackFor = RuntimeException.class)
  public void save(Article article) {
    articleExcerptMapper.insert(ArticleConverter.convertToArticleExcerpt(article));
    articleDetailDao.insert(ArticleConverter.convertToArticleDetail(article));
  }

  /**
   * modify article
   * @param article modified article
   */
  public void modify(Article article) {
    articleRedisTemplate.delete(singleCacheKey(Long.valueOf(article.getId())));
    articleExcerptMapper.update(ArticleConverter.convertToArticleExcerpt(article));
    articleDetailDao.update(ArticleConverter.convertToArticleDetail(article));
  }

  /**
   * delete article
   * @param id article id
   */
  public void delete(Long id) {
    articleRedisTemplate.delete(singleCacheKey(id));
    articleExcerptMapper.delete(id);
    articleDetailDao.delete(String.valueOf(id));
  }

  public Integer countByUser(Long userId) {
    return articleExcerptMapper.countByUser(userId);
  }

  public LocalDateTime getLastPublishingDate(Long userId) {
    return articleExcerptMapper.getLastPublishingDate(userId);
  }

  /**
   * 生成单个实体缓存的 key
   * @param id id
   * @return key in cache
   */
  private String singleCacheKey(Long id) {
    if(null == id) {
      throw new NullPointerException("id is null");
    }
    return id + "tianhuo_article";
  }

  /**
   * 生成批量缓存的 key
   * @param id id
   * @return key
   */
  private String batchCacheKey(Long id) {
    return id + "tianhuo_article_excerpt";
  }

  /**
   * get cache time out
   * @return millisecond
   */
  private Long timeout() {
    long sixHour = 1000 * 60 * 60 * 6;
    return sixHour + (long) (Math.random() * sixHour);
  }
}
