package com.tianhuo.thkernel.application;

import com.tianhuo.sunshine.dto.ArticleDto;
import com.tianhuo.sunshine.dto.CategoryDto;
import com.tianhuo.sunshine.enums.UserOperateResult;
import com.tianhuo.sunshine.exception.ArticleCategoryInvalidException;
import com.tianhuo.thkernel.application.cmd.ArticleUpdateCmd;
import com.tianhuo.thkernel.application.cmd.UserUpdateCmd;
import com.tianhuo.thkernel.domain.article.Article;
import com.tianhuo.thkernel.domain.article.ArticleService;
import com.tianhuo.thkernel.domain.category.Category;
import com.tianhuo.thkernel.domain.category.CategoryService;
import com.tianhuo.thkernel.domain.user.User;
import com.tianhuo.thkernel.domain.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * user application service
 * @author liguowei
 * @date 2019-12-14 13:59:02
 */
@Service
public class UserApplicationService {


  @Autowired
  private UserService userService;

  @Autowired
  private ArticleService articleService;

  @Autowired
  private CategoryService categoryService;

  /**
   * user register
   * @param user user domain object to register
   * @return user id
   */
  public Long register(User user) {
    if(null == user) {
      return null;
    }
    if(null == userService.getByUsername(user.getUsername())) {
      return userService.add(user);
    }
    return null;
  }

  /**
   * login
   * @param username username
   * @param password password
   * @return login result
   */
  public UserOperateResult login(String username, String password) {
    if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
      return UserOperateResult.INVALID_PARAM;
    }
    User user = userService.getByUsername(username);
    if(user == null) {
      return UserOperateResult.USER_NOT_FOUND;
    }
    if(!Objects.equals(password, user.getPassword())) {
      return UserOperateResult.PASSWORD_ERROR;
    }
    return UserOperateResult.SUCCESS;
  }

  /**
   * find user by user id
   * @param id
   * @return
   */
  public User findUserById(String id) {
    if(StringUtils.isEmpty(id)) {
      return null;
    }
    return userService.getById(id);
  }

  public List<User> queryUsers(Collection<String> ids) {
    return userService.queryUsers(ids);
  }

  /**
   * update user
   * @param cmd
   * @return
   */
  public UserOperateResult updateUserInfo(UserUpdateCmd cmd) {
    if(null == cmd || StringUtils.isEmpty(cmd.getId())) {
      return UserOperateResult.INVALID_PARAM;
    }
    User user = userService.getById(cmd.getId());
    if(null == user) {
      return UserOperateResult.USER_NOT_FOUND;
    }
    user.update(cmd);
    userService.update(user);
    return UserOperateResult.SUCCESS;
  }

  /**
   * find article by article id
   * @param articleId article id
   * @return article domain object
   */
  public ArticleDto findArticleById(String articleId) {
    if(StringUtils.isEmpty(articleId)) {
      return null;
    }
    Article article = articleService.queryById(articleId);
    if(null == article) {
      return null;
    }
    Category category = categoryService.findById(article.getCategoryId());
    if(null == category) {
      throw new ArticleCategoryInvalidException("文章分类没找到，文章id：" + article.getId());
    }
    return assemble(article, category);
  }

  /**
   * publishing article
   * @param article article to publish
   * @return operate result
   */
  public UserOperateResult publishing(Article article) {
    if(null == article) {
      return UserOperateResult.INVALID_PARAM;
    }
    articleService.publish(article);
    return UserOperateResult.SUCCESS;
  }

  /**
   * update article
   * @param cmd update article command
   * @return operate result
   */
  public UserOperateResult updateArticle(ArticleUpdateCmd cmd) {
    if(null == cmd) {
      return UserOperateResult.INVALID_PARAM;
    }
    Article article = articleService.queryById(cmd.getId());
    if(!Objects.equals(cmd.getSenderId(), article.getSenderId())) {
      return UserOperateResult.ONLY_MODIFY_YOURS_ARTICLE;
    }
    article.update(cmd);
    articleService.modify(article);
    return UserOperateResult.SUCCESS;
  }

  /**
   * get article list
   * @param start start id
   * @param limit list size
   * @return Article list
   */
  public List<ArticleDto> articleList(Long start, Integer limit) {
    List<ArticleDto> result = new ArrayList<>(null == limit ? 10 : limit);
    List<Article> articles = articleService.queryByBatch(start, limit);
    for (Article article : articles) {
      Category category = categoryService.findById(article.getCategoryId());
      if(null == category) {
        throw new ArticleCategoryInvalidException("文章分类没找到，文章id：" + article.getId());
      }
      result.add(assemble(article, category));
    }
    return result;
  }

  /**
   * article category list
   * @return list of category dto
   */
  public List<CategoryDto> categoryList() {
    return categoryService.list().stream()
        .map(this::convert).collect(Collectors.toList());
  }

  /**
   * find article list by category id
   * @param categoryId category id
   * @return list of article dto
   */
  public List<ArticleDto> findArticleByCategoryId(String categoryId, Long start, Integer limit) {
    return articleService.queryByCategoryId(categoryId, start, limit).stream()
        .map(this::convertByArticleExcerpt)
        .collect(Collectors.toList());
  }

  /**
   * article domain object convert to article dto
   * @param article article domain object
   * @return article dto
   */
  private ArticleDto assemble(Article article, Category category) {
    if(null == article) {
      return null;
    }
    return ArticleDto.builder()
        .id(article.getId())
        .title(article.getTitle())
        .senderId(article.getSenderId())
        .categoryId(String.valueOf(category.getId()))
        .categoryName(category.getName())
        .tags(article.getTags())
        .excerpt(article.getExcerpt())
        .detail(article.getDetail())
        .createAt(article.getCreateAt())
        .modifyAt(article.getModifyAt())
        .userOperateResult(UserOperateResult.SUCCESS)
        .build();
  }

  /**
   * category domain object to category dto
   * @param category category domain object
   * @return category dto
   */
  private CategoryDto convert(Category category) {
    return CategoryDto.builder()
        .id(String.valueOf(category.getId()))
        .name(category.getName())
        .build();
  }

  /**
   * article domain object to article dto
   * @param article article domain object
   * @return article dto
   */
  private ArticleDto convertByArticleExcerpt(Article article) {
    return ArticleDto.builder()
        .id(article.getId())
        .senderId(article.getSenderId())
        .title(article.getTitle())
        .categoryId(article.getCategoryId())
        .tags(article.getTags())
        .excerpt(article.getExcerpt())
        .detail(article.getDetail())
        .createAt(article.getCreateAt())
        .modifyAt(article.getModifyAt())
        .build();
  }
}
