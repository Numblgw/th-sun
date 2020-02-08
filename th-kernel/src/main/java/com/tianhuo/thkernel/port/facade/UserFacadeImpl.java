package com.tianhuo.thkernel.port.facade;

import com.alibaba.dubbo.config.annotation.Service;
import com.tianhuo.sunshine.dto.UserDto;
import com.tianhuo.sunshine.service.UserFacade;
import com.tianhuo.thcommon.dto.HttpResultWrapper;
import com.tianhuo.thcommon.enums.HttpResultStatus;
import com.tianhuo.thkernel.application.UserApplicationService;
import com.tianhuo.thkernel.application.cmd.UserUpdateCmd;
import com.tianhuo.thkernel.domain.user.User;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

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
    Long userId = userApplicationService.register(convert(userDto));
    if(null == userId || userId == 0L) {
      return new HttpResultWrapper<UserDto>().fail(HttpResultStatus.FAIL);
    }
    userDto.setId(String.valueOf(userId));
    return new HttpResultWrapper<UserDto>().success(userDto);
  }

  @Override
  public HttpResultWrapper<UserDto> login(UserDto userDto) {
    if(StringUtils.isEmpty(userDto.getUsername())
        || StringUtils.isEmpty(userDto.getPassword())) {
      return new HttpResultWrapper<UserDto>().fail(HttpResultStatus.INVALID_PARAM);
    }
    userDto.setUserOperateResult(
        userApplicationService.login(userDto.getUsername(), userDto.getPassword())
    );
    return new HttpResultWrapper<UserDto>().success(userDto);
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
        .map(this::convert)
        .collect(Collectors.toList());
    return new HttpResultWrapper<List<UserDto>>().success(res);
  }

  /**
   * convert UserDto --> User
   * @param userDto user dto
   * @return user domain object
   */
  private User convert(UserDto userDto) {
    if(userDto == null) {
      return null;
    }
    return new User(
        userDto.getId(),
        userDto.getUsername(),
        userDto.getPassword(),
        userDto.getNickname(),
        userDto.getRegisteredTime(),
        userDto.getRoleId()
    );
  }

  /**
   * user domain object convert to user data transfer object
   * @param user user domain object
   * @return user data transfer object
   */
  private UserDto convert(User user) {
    if(user == null) {
      return null;
    }
    return UserDto.builder()
        .id(user.getId())
        .username(user.getUsername())
        .nickname(user.getNickname())
        .registeredTime(user.getRegisteredTime())
        .roleId(user.getRoleId())
        .build();
  }
}
