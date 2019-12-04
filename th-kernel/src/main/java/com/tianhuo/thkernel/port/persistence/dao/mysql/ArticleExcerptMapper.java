package com.tianhuo.thkernel.port.persistence.dao.mysql;

import com.tianhuo.thkernel.port.persistence.entity.ArticleExcerptDO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
      + "select id, title, category_id, category_name, tags, excerpt, create_at, modify_at "
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
      "select id, title, category_id, category_name, tags, excerpt, create_at, modify_at "
      + "from article_excerpt "
      + "where id > #{start} limit #{limit}"
  )
  List<ArticleExcerptDO> queryByBatch(Long start, Integer limit);

  /**
   * query article by id
   * @param id article id
   * @return article excerpt data object
   */
  @Select(
      "select id, title, category_id, category_name, tags, excerpt, create_at, modify_at "
          + "from article_excerpt "
          + "where id = #{id}"
  )
  ArticleExcerptDO queryById(Long id);

  /**
   * update
   * @param excerptDO data object
   */
  void update(ArticleExcerptDO excerptDO);

  /**
   * delete article by id
   * @param id article id
   */
  void delete(Long id);

  /**
   * insert
   * @param articleExcerptDO data object
   */
  @Insert(
      "insert into "
      + "article_excerpt(title, category_id, category_name, tags, excerpt, create_at, modify_at) "
      + "values(#{title}, #{categoryId}, #{categoryName}, #{tags}, #{excerpt}, #{createAt}, #{modifyAt})"
  )
  void insert(ArticleExcerptDO articleExcerptDO);
}
