package com.tianhuo.thkernel.port.facade;

import com.alibaba.dubbo.config.annotation.Service;
import com.tianhuo.sunshine.dto.UserDto;
import com.tianhuo.sunshine.service.SessionFacade;
import com.tianhuo.thcommon.dto.HttpResultWrapper;
import com.tianhuo.thkernel.domain.session.SessionId;
import com.tianhuo.thkernel.domain.session.SessionService;
import com.tianhuo.thkernel.domain.user.repository.UserConverter;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * impl
 * @see com.tianhuo.sunshine.service.SessionFacade
 * @author liguowei
 * @date 2020-02-10 21:58:20
 */
@Component
@Service
public class SessionFacadeImpl implements SessionFacade {

  @Resource
  private SessionService sessionService;

  @Override
  public HttpResultWrapper<Boolean> isValid(String sessionId) {
    SessionId session = sessionService.getSession(sessionId);
    return new HttpResultWrapper<Boolean>()
        .success(session.isValid());
  }

  @Override
  public HttpResultWrapper<UserDto> getLoginUser(String sessionId) {
    SessionId session = sessionService.getSession(sessionId);
    if(session.isValid()) {
      return new HttpResultWrapper<UserDto>()
          .success(UserConverter.toUserDTO(session.getUser(), sessionId, session.getLifeTime()));
    }
    return new HttpResultWrapper<UserDto>().fail();
  }

  @Override
  public HttpResultWrapper<Boolean> removeSession(String sessionId) {
    sessionService.removeSession(sessionId);
    return new HttpResultWrapper<Boolean>().success(Boolean.TRUE);
  }
}
