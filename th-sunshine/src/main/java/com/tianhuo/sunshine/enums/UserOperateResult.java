package com.tianhuo.sunshine.enums;

/**
 * user operate result
 * @author liguowei
 * @date 2019-12-14 14:54:10
 */
public enum UserOperateResult {

  /**
   * 参数错误，例如参数为 null
   */
  INVALID_PARAM,

  /**
   * 成功
   */
  SUCCESS,

  /**
   * 需要登录
   */
  NEED_LOGIN,

  /**
   * 找不到用户
   */
  USER_NOT_FOUND,

  /**
   * 密码错误
   */
  PASSWORD_ERROR,

  /**
   * 只能修改自己的文章
   */
  ONLY_MODIFY_YOURS_ARTICLE,

  ;
}
