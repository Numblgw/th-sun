package com.tianhuo.thkernel.port.persistence.dao.mysql;

import com.tianhuo.thkernel.port.persistence.entity.ArticleExcerptDO;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

/**
 * article mapper
 * @author liguowei
 * @date 2019-11-25 16:50:58
 */
@Mapper
public interface ArticleExcerptMapper {

  /**
   * query article by id list
   * @param ids id list
   * @return article excerpt list
   */
  @Select("<script> "
      + "select id, sender_id, title, category_id, tags, excerpt, create_at, modify_at "
      + "from article_excerpt "
      + "where id in "
      + "<foreach item='item' collection='ids' open='(' separator=', ' close=')'> "
      + "#{item}"
      + "</foreach>"
      + "</script>")
  List<ArticleExcerptDO> queryByIds(@Param("ids") List<Long> ids);

  /**
   * query aritcle by batch
   * @param start start at
   * @param limit batch size
   * @return article list
   */
  @Select(
      "select id, sender_id, title, category_id, tags, excerpt, create_at, modify_at "
      + "from article_excerpt "
      + "limit #{start}, #{limit} "
  )
  List<ArticleExcerptDO> queryByBatch(Long start, Integer limit);

  /**
   * query article by id
   * @param id article id
   * @return article excerpt data object
   */
  @Select(
      "select id, sender_id, title, category_id, tags, excerpt, create_at, modify_at "
          + "from article_excerpt "
          + "where id = #{id}"
  )
  ArticleExcerptDO queryById(Long id);

  /**
   * update
   * @param excerptDO data object
   */
  @Update("update article_excerpt "
      + "set "
      + "title = #{title}, "
      + "category_id = #{categoryId}, "
      + "tags = #{tags}, "
      + "excerpt = #{excerpt}, "
      + "modify_at = #{modifyAt} "
      + "where id = #{id} ")
  void update(ArticleExcerptDO excerptDO);

  /**
   * delete article by id
   * @param id article id
   */
  @Delete("delete from article_excerpt where id = #{id}")
  void delete(Long id);

  /**
   * insert
   * @param articleExcerptDO data object
   * @return article id
   */
  @Insert(
      "insert into "
      + "article_excerpt(sender_id, title, category_id, tags, excerpt, create_at, modify_at) "
      + "values(#{senderId}, #{title}, #{categoryId}, #{tags}, #{excerpt}, #{createAt}, #{modifyAt})"
  )
  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  Long insert(ArticleExcerptDO articleExcerptDO);

  /**
   * count article by user id
   * @param userId user id
   * @return article count
   */
  @Select("select count(*) from article_excerpt where sender_id = #{userId}")
  Integer countByUser(Long userId);

  /**
   * get last publishing date by user id
   * @param userId user id
   * @return last publish date
   */
  @Select("select max(create_at) from article_excerpt where sender_id = #{userId}")
  LocalDateTime getLastPublishingDate(Long userId);

  /**
   * query by category id
   * @param categoryId category id
   * @return list of article excerpt data object
   */
  @Select(
      "select id, sender_id, title, category_id, tags, excerpt, create_at, modify_at "
          + "from article_excerpt "
          + "where category_id = #{categoryId} limit #{start}, #{limit}"
  )
  List<ArticleExcerptDO> queryByCategoryId(Integer categoryId, Long start, Integer limit);

  /**
   * count article
   * @return article total count
   */
  @Select("select count(*) from article_excerpt")
  Long countArticle();
}
