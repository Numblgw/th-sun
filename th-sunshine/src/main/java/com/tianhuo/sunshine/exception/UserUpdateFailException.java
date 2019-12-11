package com.tianhuo.sunshine.exception;

import com.tianhuo.thcommon.exception.TianHuoBaseException;

/**
 * update user fail exception
 * @author liguowei
 * @date 2019-12-11 18:13:44
 */
public class UserUpdateFailException extends TianHuoBaseException {

  private static final long serialVersionUID = -5983853461325545528L;

  private static final String DEFAULT_MSG = "update user fail";

  public UserUpdateFailException() {
    super(DEFAULT_MSG);
  }

  public UserUpdateFailException(String msg) {
    super(msg);
  }
}
