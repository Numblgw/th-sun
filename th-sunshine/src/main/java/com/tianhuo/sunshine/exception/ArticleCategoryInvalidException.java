package com.tianhuo.sunshine.exception;

import com.tianhuo.thcommon.exception.TianHuoBaseException;

/**
 * article category invalid exception
 * @author liguowei
 * @date 2019-12-15 16:08:06
 */
public class ArticleCategoryInvalidException extends TianHuoBaseException {

  private static final long serialVersionUID = -4677693581598123725L;

  private static final String DEFAULT_MSG = "article category invalid";

  public ArticleCategoryInvalidException() {
    super(DEFAULT_MSG);
  }

  public ArticleCategoryInvalidException(String msg) {
    super(msg);
  }
}
