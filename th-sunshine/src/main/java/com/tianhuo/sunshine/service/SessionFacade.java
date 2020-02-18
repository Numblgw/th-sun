package com.tianhuo.sunshine.service;

import com.tianhuo.sunshine.dto.UserDto;
import com.tianhuo.thcommon.dto.HttpResultWrapper;

/**
 * session facade
 * @author liguowei
 * @date 2020-02-10 21:52:53
 */
public interface SessionFacade {

  /**
   * session is valid
   * @param sessionId session id
   * @return if valid return true else return false
   */
  HttpResultWrapper<Boolean> isValid(String sessionId);

  /**
   * get login by session id
   * @param sessionId session id
   * @return user dto
   */
  HttpResultWrapper<UserDto> getLoginUser(String sessionId);

  /**
   * remove session
   * @param sessionId session id
   * @return success true, if throw exception false
   */
  HttpResultWrapper<Boolean> removeSession(String sessionId);
}
