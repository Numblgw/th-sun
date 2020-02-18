package com.tianhuo.thkernel.domain.session;

import com.alibaba.druid.util.StringUtils;
import com.tianhuo.thkernel.domain.user.User;
import com.tianhuo.thkernel.domain.user.repository.UserConverter;
import com.tianhuo.thkernel.port.persistence.entity.UserCacheDO;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.concurrent.TimeUnit;

/**
 * @see SessionService
 * @author liguowei
 * @date 2020-02-09 21:53:57
 */
@Service
public class SessionServiceImpl implements SessionService {

  /**
   * redis 中的 session key 前缀
   */
  private static final String SESSION_ID_CACHE_KEY = "th:sun:user:session:";

  /**
   * session 过期时间
   */
  private static final long SESSION_TIME_OUT = (long) 1000 * 60 * 60 * 24 * 7;

  @Resource
  private RedisTemplate<String, SessionCacheDO> sessionTemplate;

  @Override
  public SessionId createSession(User user) {
    if(StringUtils.isEmpty(user.getId())) {
      return SessionId.empty();
    }
    String sessionId = generateSessionId(user.getId());
    UserCacheDO userCache = UserConverter.toUserCache(user);
    Long timeOut = sessionTimeOut();
    Long createTime = System.currentTimeMillis();
    sessionTemplate.opsForValue()
        .set(getCacheKey(sessionId), new SessionCacheDO(userCache, timeOut, createTime), timeOut, TimeUnit.MILLISECONDS);
    return SessionId.createSession(sessionId, user, timeOut, createTime);
  }

  @Override
  public SessionId getSession(String sessionId) {
    SessionCacheDO sessionCache = sessionTemplate.opsForValue().get(getCacheKey(sessionId));
    if(null == sessionCache) {
      return SessionId.empty();
    }
    return SessionId.createSession(sessionId, UserConverter.convert(sessionCache.getUserCache()), sessionCache.getLifeTime(), sessionCache.getCreateTime());
  }

  @Override
  public void removeSession(String sessionId) {
    sessionTemplate.delete(getCacheKey(sessionId));
  }

  /**
   * generate session id
   * @param uid  user id
   * @return session id
   */
  private String generateSessionId(String uid) {
    return uid + System.currentTimeMillis();
  }

  /**
   * get session time out
   * @return long of
   */
  private long sessionTimeOut() {
    return SESSION_TIME_OUT;
  }

  /**
   * get session id cache key
   * @param sessionId session id
   * @return key in redis
   */
  private String getCacheKey(String sessionId) {
    return SESSION_ID_CACHE_KEY + sessionId;
  }
}
