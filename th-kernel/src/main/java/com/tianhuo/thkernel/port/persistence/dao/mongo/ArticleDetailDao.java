package com.tianhuo.thkernel.port.persistence.dao.mongo;

import com.tianhuo.thkernel.port.persistence.entity.ArticleDetailDO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * article dao (MongoDB)
 * @author liguowei
 * @date 2019-11-25 21:08:11
 */
@Repository
public class ArticleDetailDao {

  /**
   * MongoDB collection name
   */
  private static final String COLLECTION_NAME = "tianhuo_article";

  @Resource
  private MongoTemplate mongoTemplate;

  /**
   * insert one article detail
   * @param articleDetail
   */
  public void insert(ArticleDetailDO articleDetail) {
    mongoTemplate.save(articleDetail, COLLECTION_NAME);
  }

  /**
   * update article detail
   * @param articleDetail article detail do
   */
  @Transactional(rollbackFor = RuntimeException.class)
  public void update(ArticleDetailDO articleDetail) {
    mongoTemplate.save(articleDetail, COLLECTION_NAME);
  }

  /**
   * delete one article
   * @param id article id
   */
  public void delete(String id) {
    mongoTemplate.remove(
        new Query(Criteria.where("id").is(id)),
        ArticleDetailDO.class, COLLECTION_NAME
    );
  }

  public ArticleDetailDO queryById(String id) {
    return mongoTemplate.findOne(
        new Query(Criteria.where("id").is(id)),
        ArticleDetailDO.class, COLLECTION_NAME
    );
  }
}
