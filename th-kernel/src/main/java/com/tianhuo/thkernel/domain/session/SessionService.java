package com.tianhuo.thkernel.domain.session;

import com.tianhuo.thkernel.domain.user.User;

/**
 * 分布式 session 管理
 * @author liguowei
 * @date 2020-02-09 21:45:55
 */
public interface SessionService {

  /**
   * create session by user
   * @param user user
   * @return session id domain object
   */
  SessionId createSession(User user);

  /**
   * get login user by session id
   * @param sessionId session id
   * @return login user info
   */
  SessionId getSession(String sessionId);

  /**
   * remove session
   * @param sessionId session id
   */
  void removeSession(String sessionId);
}
