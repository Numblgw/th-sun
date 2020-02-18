package com.tianhuo.thkernel.domain.session;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tianhuo.thkernel.domain.user.User;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * session id
 * @author liguowei
 * @date 2020-02-10 20:10:17
 */
@Getter
@ToString
@EqualsAndHashCode
public class SessionId {

  private static final SessionId INVALID_SESSION = new SessionId();

  /**
   * session id
   */
  private String sessionId;

  /**
   * user domain object
   */
  private User user;

  /**
   * session 有效时间，单位（毫秒）
   */
  private Long lifeTime;

  /**
   * session 创建时间
   */
  private Long createTime;


  /**
   * create session domain object
   * @param sessionId session id
   * @param user user domain object
   * @param lifeTime life time
   * @param createTime create time
   * @return session domain object
   */
  static SessionId createSession(String sessionId, User user, Long lifeTime, Long createTime) {
    SessionId session = new SessionId();
    session.sessionId = sessionId;
    session.user = user;
    session.lifeTime = lifeTime;
    session.createTime = createTime;
    if(session.check()) {
      return session;
    }
    return INVALID_SESSION;
  }

  /**
   * return a invalid session
   * @return invalid session
   */
  public static SessionId empty() {
    return INVALID_SESSION;
  }

  /**
   * 设置声明周期，不可重复设置，不能设置为 null
   * @param lifeTime 生命周期时间，单位（毫秒）
   */
  public void setLifeTime(Long lifeTime) {
    if(null == lifeTime || this.lifeTime != null) {
      return;
    }
    this.lifeTime = lifeTime;
  }

  /**
   * 是否有效
   * @return 有效 true 无效 false
   */
  public boolean isValid() {
    return this != INVALID_SESSION
        && (System.currentTimeMillis() - this.createTime) < this.lifeTime;
  }

  private boolean check() {
    if(StringUtils.isEmpty(this.sessionId)) {
      return false;
    }
    if(null == user || StringUtils.isEmpty(user.getId())) {
      return false;
    }
    if(null == lifeTime || lifeTime <= 0) {
      return false;
    }
    if(null == createTime || createTime <= 0) {
      return false;
    }
    return this.isValid();
  }
}
