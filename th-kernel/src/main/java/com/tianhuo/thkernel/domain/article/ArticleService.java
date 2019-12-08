package com.tianhuo.thkernel.domain.article;

import java.time.LocalDateTime;
import java.util.List;


/**
 * article domain service
 * @author liguowei
 * @date 2019-11-23 17:53:35
 */
public interface ArticleService {

  /**
   * query article by id
   * @param id article id
   * @return article
   */
  Article queryById(Long id);

  /**
   * query article by batch
   * @param start start id
   * @param limit batch size
   * @return article list
   */
  List<Article> queryByBatch(Long start, Integer limit);

  /**
   * publish article
   * @param article article domain object
   */
  void publish(Article article);

  /**
   * modify article
   * @param article modified article
   */
  void modify(Article article);

  /**
   * delete article
   * @param id article id
   */
  void delete(Long id);

  /**
   * count article by user id
   * @param userId user id
   * @return article count
   */
  Integer countByUserId(Long userId);

  /**
   * get last publishing date by user
   * @param userId user id
   * @return last publishing date
   */
  LocalDateTime getLastPublishingDateByUser(Long userId);
}
