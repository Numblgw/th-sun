package com.tianhuo.thkernel.port.facade;

import com.alibaba.dubbo.config.annotation.Service;
import com.tianhuo.sunshine.dto.ArticleDto;
import com.tianhuo.sunshine.service.ArticleFacade;
import com.tianhuo.thcommon.dto.HttpResultWrapper;
import com.tianhuo.thcommon.enums.HttpResultStatus;
import com.tianhuo.thkernel.application.UserApplicationService;
import com.tianhuo.thkernel.application.cmd.ArticleUpdateCmd;
import com.tianhuo.thkernel.domain.article.Article;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.List;

/**
 * article facade impl
 * @author liguowei
 * @date 2019-12-14 22:02:12
 */
@Service
@Component
public class ArticleFacadeImpl implements ArticleFacade {

  @Resource
  private UserApplicationService applicationService;

  @Override
  public HttpResultWrapper<ArticleDto> findById(String id) {
    return new HttpResultWrapper<ArticleDto>()
        .success(applicationService.findArticleById(id));
  }

  @Override
  public HttpResultWrapper<ArticleDto> publishing(ArticleDto articleDto) {
    if(null == articleDto) {
      return new HttpResultWrapper<ArticleDto>()
          .fail(HttpResultStatus.INVALID_PARAM);
    }
    articleDto.setCreateAt(LocalDateTime.now());
    Article article = new Article(
        articleDto.getId(),
        articleDto.getSenderId(),
        articleDto.getTitle(),
        articleDto.getCategoryId(),
        articleDto.getTags(),
        articleDto.getDetail(),
        articleDto.getCreateAt(),
        articleDto.getModifyAt()
    );
    articleDto.setUserOperateResult(applicationService.publishing(article));
    articleDto.setId(article.getId());
    return new HttpResultWrapper<ArticleDto>().success(articleDto);
  }

  @Override
  public HttpResultWrapper<ArticleDto> update(ArticleDto articleDto) {
    try {
      articleDto = applicationService.updateArticle(createUpdateCmd(articleDto));
    }catch(Exception e) {
      articleDto = null;
    }
    return new HttpResultWrapper<ArticleDto>().success(articleDto);
  }

  @Override
  public HttpResultWrapper<List<ArticleDto>> list(Long start, Integer limit) {
    return new HttpResultWrapper<List<ArticleDto>>()
        .success(applicationService.articleList(start, limit), applicationService.articleCount());
  }

  @Override
  public HttpResultWrapper<List<ArticleDto>> listByCategory(String categoryId, Long start,
      Integer limit) {
    return new HttpResultWrapper<List<ArticleDto>>()
        .success(applicationService.findArticleByCategoryId(categoryId, start, limit));
  }

  @Override
  public HttpResultWrapper<Boolean> delete(Long id) {
    Boolean result = applicationService.deleteArticle(id);
    return new HttpResultWrapper<Boolean>().success(result);
  }

  /**
   * create article update command
   * @param articleDto article dto
   * @return article update command
   */
  private ArticleUpdateCmd createUpdateCmd(ArticleDto articleDto) {
    if(null == articleDto) {
      return ArticleUpdateCmd.builder().build();
    }
    return ArticleUpdateCmd.builder()
        .id(articleDto.getId())
        .senderId(articleDto.getSenderId())
        .categoryId(String.valueOf(articleDto.getCategoryId()))
        .detail(articleDto.getDetail())
        .tags(articleDto.getTags())
        .title(articleDto.getTitle())
        .build();
  }
}
