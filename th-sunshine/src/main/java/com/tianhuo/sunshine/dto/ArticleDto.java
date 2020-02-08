package com.tianhuo.sunshine.dto;

import com.tianhuo.sunshine.enums.UserOperateResult;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * article dto
 * @author liguowei
 * @date 2019-12-14 21:04:07
 */
@Data
@Builder
public class ArticleDto implements Serializable {

  private static final long serialVersionUID = 6788141127260808397L;

  /**
   * 用于保存业务上的操作结果
   */
  private UserOperateResult userOperateResult;

  private String id;

  private String senderId;

  private String title;

  private String categoryId;

  private String categoryName;

  private List<String> tags;

  private String excerpt;

  private String detail;

  private LocalDateTime createAt;

  private LocalDateTime modifyAt;
}
