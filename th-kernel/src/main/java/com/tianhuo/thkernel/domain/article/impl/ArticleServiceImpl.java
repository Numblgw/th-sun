package com.tianhuo.thkernel.domain.article.impl;

import com.tianhuo.thkernel.domain.article.ArticleService;
import com.tianhuo.thkernel.domain.article.repository.ArticleRepository;
import com.tianhuo.thcommon.domain.Article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @see com.tianhuo.thkernel.domain.article.ArticleService
 *
 * @author liguowei
 * @date 2019-11-23 22:55:56
 */
@Service
public class ArticleServiceImpl implements ArticleService {

  /**
   * batch max size
   */
  private static final int BATCH_MAX_SIZE = 30;

  private final ArticleRepository articleRepository;

  @Autowired
  public ArticleServiceImpl(ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  @Override
  public Article queryById(Long id) {
    if(null == id || 0 == id) {
      return null;
    }
    return articleRepository.queryById(id);
  }

  @Override
  public List<Article> queryByBatch(Long start, Integer limit) {
    if(null == start || start < 0) {
      start = 0L;
    }
    if(null == limit || limit > BATCH_MAX_SIZE) {
      limit = BATCH_MAX_SIZE;
    }
    return articleRepository.queryByBatch(start, limit);
  }

  @Override
  public void publish(Article article) {
    if(null == article) {
      return;
    }
    articleRepository.save(article);
  }

  @Override
  public void modify(Article article) {
    if(null == article) {
      return;
    }
    articleRepository.modify(article);
  }

  @Override
  public void delete(Long id) {
    if(null == id || 0 == id) {
      return;
    }
    articleRepository.delete(id);
  }

  @Override
  public Integer countByUserId(Long userId) {
    if(null == userId) {
      return 0;
    }
    return articleRepository.countByUser(userId);
  }

  @Override
  public LocalDateTime getLastPublishingDateByUser(Long userId) {
    if(null == userId) {
      return null;
    }
    return articleRepository.getLastPublishingDate(userId);
  }
}
