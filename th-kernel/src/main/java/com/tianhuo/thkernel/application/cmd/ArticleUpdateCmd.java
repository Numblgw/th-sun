package com.tianhuo.thkernel.application.cmd;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * update article command
 * @author liguowei
 * @date 2019-12-09 21:55:58
 */
@Data
@Builder
public class ArticleUpdateCmd {

  private String id;

  private String senderId;

  private String title;

  private String categoryId;

  private List<String> tags;

  private String detail;
}
