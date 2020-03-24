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
import com.tianhuo.thkernel.domain.session.SessionId;
import com.tianhuo.thkernel.domain.session.SessionService;
import com.tianhuo.thkernel.domain.user.User;
import com.tianhuo.thkernel.domain.user.UserService;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

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


  @Resource
  private UserService userService;

  @Resource
  private ArticleService articleService;

  @Resource
  private CategoryService categoryService;

  @Resource
  private SessionService sessionService;

  /**
   * user register
   * @param user user domain object to register
   * @return user id
   */
  public SessionId register(User user) {
    if(null == user) {
      return null;
    }
    if(null == userService.getByUsername(user.getUsername())) {
      userService.add(user);
    }
    // 如果插入失败，user id 就是空的，这里会返回一个 Empty Session
    return sessionService.createSession(user);
  }

  /**
   * login
   * @param username username
   * @param password password
   * @return session id domain object
   */
  public SessionId login(String username, String password) {
    if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
      return null;
    }
    User user = userService.getByUsername(username);
    if(user == null) {
      return null;
    }
    if(!Objects.equals(password, user.getPassword())) {
      return SessionId.empty();
    }
    return sessionService.createSession(user);
  }

  /**
   * find user by user id
   * @param id user id
   * @return user domain object
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

  public List<User> userList() {
    return userService.userList();
  }

  public void deleteUser(String id) {
    userService.delete(id);
  }

  /**
   * update user
   * @param cmd update user command
   * @return .
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
  public ArticleDto updateArticle(ArticleUpdateCmd cmd) {
    if(null == cmd) {
      return null;
    }
    Article article = articleService.queryById(cmd.getId());
    article.update(cmd);
    articleService.modify(article);
    Category category = categoryService.findById(article.getCategoryId());
    return assemble(article, category);
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

  public Long articleCount() {
    return articleService.countArticle();
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
    List<ArticleDto> result = new ArrayList<>(null == limit ? 10 : limit);
    List<Article> articles = articleService.queryByCategoryId(categoryId, start, limit);
    for (Article article : articles) {
      Category category = categoryService.findById(article.getCategoryId());
      if(null == category) {
        throw new ArticleCategoryInvalidException("文章分类没找到，文章id：" + article.getId());
      }
      result.add(assemble(article, category));
    }
    return result;
  }

  public Boolean deleteArticle(Long id) {
    articleService.delete(id);
    return true;
  }

  public boolean grant(String uid, String roleId) {
    return userService.grant(uid, roleId);
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
