package com.tianhuo.thkernel.port.facade;

import com.alibaba.dubbo.config.annotation.Service;
import com.tianhuo.sunshine.dto.UserDto;
import com.tianhuo.sunshine.enums.UserOperateResult;
import com.tianhuo.sunshine.service.UserFacade;
import com.tianhuo.thcommon.dto.HttpResultWrapper;
import com.tianhuo.thcommon.enums.HttpResultStatus;
import com.tianhuo.thkernel.application.UserApplicationService;
import com.tianhuo.thkernel.application.cmd.UserUpdateCmd;
import com.tianhuo.thkernel.domain.session.SessionId;
import com.tianhuo.thkernel.domain.user.User;
import com.tianhuo.thkernel.domain.user.repository.UserConverter;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * user rpc service impl
 * @see com.tianhuo.sunshine.service.UserFacade
 * @author liguowei
 * @date 2019-12-07 22:40:33
 */
@Service
@Component
public class UserFacadeImpl implements UserFacade {

  @Resource
  private UserApplicationService userApplicationService;

  @Override
  public HttpResultWrapper<UserDto> register(UserDto userDto) {
    if(null == userDto) {
      return new HttpResultWrapper<UserDto>()
          .fail(HttpResultStatus.INVALID_PARAM);
    }
    userDto.setRegisteredTime(LocalDateTime.now());
    userDto.setRoleId("1");
    SessionId sessionId = userApplicationService.register(UserConverter.convert(userDto));
    if(!sessionId.isValid()) {
      return new HttpResultWrapper<UserDto>().fail(HttpResultStatus.FAIL);
    }
    return new HttpResultWrapper<UserDto>().success(convertBySessionId(sessionId));
  }

  @Override
  public HttpResultWrapper<UserDto> login(UserDto userDto) {
    if(StringUtils.isEmpty(userDto.getUsername())
        || StringUtils.isEmpty(userDto.getPassword())) {
      return new HttpResultWrapper<UserDto>().fail(HttpResultStatus.INVALID_PARAM);
    }
    SessionId sessionId = userApplicationService.login(userDto.getUsername(), userDto.getPassword());
    return new HttpResultWrapper<UserDto>().success(convertBySessionId(sessionId));
  }

  @Override
  public HttpResultWrapper<UserDto> update(UserDto userDto) {
    if(null == userDto || StringUtils.isEmpty(userDto.getId())) {
      return new HttpResultWrapper<UserDto>().fail(HttpResultStatus.INVALID_PARAM);
    }
    UserUpdateCmd cmd = UserUpdateCmd.builder()
        .id(userDto.getId())
        .nickname(userDto.getNickname())
        .password(userDto.getPassword())
        .roleId(userDto.getRoleId())
        .build();
    userDto.setUserOperateResult(userApplicationService.updateUserInfo(cmd));
    return new HttpResultWrapper<UserDto>().success(userDto);
  }

  @Override
  public HttpResultWrapper<List<UserDto>> queryUsers(Collection<String> ids) {
    List<UserDto> res = userApplicationService.queryUsers(ids).stream()
        .map(UserConverter::toUserDTO)
        .collect(Collectors.toList());
    return new HttpResultWrapper<List<UserDto>>().success(res);
  }

  @Override
  public HttpResultWrapper<List<UserDto>> userList() {
    List<UserDto> res = userApplicationService.userList().stream()
        .map(UserConverter::toUserDTO)
        .collect(Collectors.toList());
    return new HttpResultWrapper<List<UserDto>>().success(res);
  }

  @Override
  public HttpResultWrapper<Boolean> delete(String id) {
    userApplicationService.deleteUser(id);
    return new HttpResultWrapper<Boolean>().success(Boolean.TRUE);
  }

  @Override
  public HttpResultWrapper<Boolean> grant(String uid, String roleId) {
    userApplicationService.grant(uid, roleId);
    return new HttpResultWrapper<Boolean>().success(Boolean.TRUE);
  }

  /**
   * process session id to user data transfer object
   * @return user data transfer object
   */
  private UserDto convertBySessionId(SessionId sessionId) {
    if(null == sessionId) {
      return UserDto.builder()
          .userOperateResult(UserOperateResult.USER_NOT_FOUND)
          .build();
    }
    User user = sessionId.getUser();
    if(!sessionId.isValid()) {
      return UserDto.builder()
          .userOperateResult(UserOperateResult.PASSWORD_ERROR)
          .username(user.getUsername())
          .build();
    }else {
      return UserDto.builder()
          .userOperateResult(UserOperateResult.SUCCESS)
          .id(user.getId())
          .sessionId(sessionId.getSessionId())
          .sessionLifeTime(sessionId.getLifeTime())
          .username(user.getUsername())
          .nickname(user.getNickname())
          .registeredTime(user.getRegisteredTime())
          .roleId(user.getRoleId())
          .build();
    }
  }
}
