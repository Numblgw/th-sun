package com.tianhuo.sunshine.exception;

import com.tianhuo.thcommon.exception.TianHuoBaseException;

/**
 * exception: article detail not found
 * @author liguowei
 * @date 2019-11-25 21:50:24
 */
public class ArticleDetailNotFoundException extends TianHuoBaseException {

  private static final long serialVersionUID = 7807684949211268007L;

  /**
   * default message for this exception
   */
  private static final String DEFAULT_MESSAGE = "article detail not found, id: ";

  public ArticleDetailNotFoundException(String id) {
    super(DEFAULT_MESSAGE + id);
  }
}
