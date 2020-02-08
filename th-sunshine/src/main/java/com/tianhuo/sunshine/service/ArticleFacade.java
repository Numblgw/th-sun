package com.tianhuo.sunshine.service;

import com.tianhuo.sunshine.dto.ArticleDto;
import com.tianhuo.thcommon.dto.HttpResultWrapper;

import java.util.List;

/**
 * @author liguowei
 * @date 2019-12-07 22:34:43
 */
public interface ArticleFacade {

  /**
   * find article by id
   * @param id article id
   * @return article dto with wrapper
   */
  HttpResultWrapper<ArticleDto> findById(String id);

  /**
   * publish article
   * @param articleDto article dto
   * @return article dto
   */
  HttpResultWrapper<ArticleDto> publishing(ArticleDto articleDto);

  /**
   * update article
   * @param articleDto article dto
   * @return article with wrapper
   */
  HttpResultWrapper<ArticleDto> update(ArticleDto articleDto);

  /**
   * article list
   * @param start start id
   * @param limit limit id
   * @return list of article dto
   */
  HttpResultWrapper<List<ArticleDto>> list(Long start, Integer limit);

  /**
   * get article dto list by category id
   * @param categoryId category id
   * @param start start
   * @param limit limit
   * @return list of article dto
   */
  HttpResultWrapper<List<ArticleDto>> listByCategory(String categoryId, Long start, Integer limit);
}
