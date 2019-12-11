package com.tianhuo.sunshine.exception;

import com.tianhuo.thcommon.exception.TianHuoBaseException;

/**
 * article update fail exception
 * @author liguowei
 * @date 2019-12-09 22:01:04
 */
public class ArticleUpdateFailException extends TianHuoBaseException {

  private static final long serialVersionUID = 6624863919801842212L;

  private static final String DEFAULT_MSG = "article update fail";

  public ArticleUpdateFailException() {
    super(DEFAULT_MSG);
  }

  public ArticleUpdateFailException(String msg) {
    super(msg);
  }
}
