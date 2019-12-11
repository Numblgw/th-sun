package com.tianhuo.sunshine.exception;

import com.tianhuo.thcommon.exception.TianHuoBaseException;

/**
 * update category fail
 * @author liguowei
 * @date 2019-12-09 22:30:54
 */
public class CategoryUpdateFailException extends TianHuoBaseException {

  private static final long serialVersionUID = 4191554521528288829L;

  private static final String DEFAULT_MSG = "update category fail";

  public CategoryUpdateFailException() {
    super(DEFAULT_MSG);
  }

  public CategoryUpdateFailException(String msg) {
    super(msg);
  }
}
