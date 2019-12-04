package com.tianhuo.thkernel.port.persistence.dao.mongo;

import com.tianhuo.thkernel.ThKernelApplication;
import com.tianhuo.thkernel.port.persistence.entity.ArticleDetailDO;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ThKernelApplication.class)
class ArticleDetailDaoTest {

  @Autowired
  private ArticleDetailDao articleDetailDao;

  @Test
  void insert() {
    ArticleDetailDO articleDetail = new ArticleDetailDO();
    articleDetail.setId("1");
    articleDetail.setDetail("asdfqwerzxcvfjjffffffffffffffffffffffff");
    articleDetailDao.insert(articleDetail);
  }

  @Test
  void update() {
    ArticleDetailDO articleDetail = new ArticleDetailDO();
    articleDetail.setId("1");
    articleDetail.setDetail("1111111111111111111111111111111111111");
    articleDetailDao.update(articleDetail);
  }

  @Test
  void delete() {
    articleDetailDao.delete("1");
  }

  @Test
  void queryById() {
    System.out.println(articleDetailDao.queryById("1"));
  }
}