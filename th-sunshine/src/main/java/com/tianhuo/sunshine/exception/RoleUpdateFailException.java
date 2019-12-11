package com.tianhuo.sunshine.exception;

import com.tianhuo.thcommon.exception.TianHuoBaseException;

/**
 * update role fail exception
 * @author liguowei
 * @date 2019-12-09 22:40:13
 */
public class RoleUpdateFailException extends TianHuoBaseException {

  private static final long serialVersionUID = 7726406426178195717L;

  private static final String DEFAULT_MSG = "update role fail";

  public RoleUpdateFailException() {
    super(DEFAULT_MSG);
  }

  public RoleUpdateFailException(String msg) {
    super(msg);
  }
}
