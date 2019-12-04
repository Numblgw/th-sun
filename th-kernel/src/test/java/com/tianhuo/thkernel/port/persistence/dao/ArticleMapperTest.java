package com.tianhuo.thkernel.port.persistence.dao;


import com.tianhuo.thkernel.ThKernelApplication;
import com.tianhuo.thkernel.port.persistence.dao.mysql.ArticleExcerptMapper;
import com.tianhuo.thkernel.port.persistence.entity.ArticleExcerptDO;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ThKernelApplication.class)
class ArticleMapperTest {

  @Autowired
  private ArticleExcerptMapper articleExcerptMapper;

  @Test
  void queryByIds() {
    System.out.println(articleExcerptMapper.queryByIds(Lists.newArrayList(10L, 11L, 12L)));
  }

  @Test
  void queryByBatch() {
    System.out.println(articleExcerptMapper.queryByBatch(0L, 4));
  }

  @Test
  void queryById() {
    ArticleExcerptDO articleExcerptDO = articleExcerptMapper.queryById(3L);
    System.out.println(articleExcerptDO.toString());
  }

  @Test
  void insert() {
    ArticleExcerptDO articleExcerpt = new ArticleExcerptDO();
    articleExcerpt.setTitle("123");
    articleExcerpt.setCategoryId(23421);
    articleExcerpt.setCategoryName("32424");
    articleExcerpt.setTags("1,3242,23424");
    articleExcerpt.setExcerpt("12341242");
    articleExcerpt.setCreateAt(LocalDateTime.now());
    articleExcerpt.setModifyAt(null);
    articleExcerptMapper.insert(articleExcerpt);
  }
}