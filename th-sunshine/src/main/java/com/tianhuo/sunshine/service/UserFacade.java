package com.tianhuo.sunshine.service;

import com.tianhuo.sunshine.dto.UserDto;
import com.tianhuo.sunshine.enums.UserOperateResult;
import com.tianhuo.thcommon.dto.HttpResultWrapper;

import java.util.Collection;
import java.util.List;

/**
 * user rpc service interface
 * @author liguowei
 * @date 2019-12-04 19:56:11
 */
public interface UserFacade {

  /**
   * register
   * @param userDto user info to register
   * @return user id
   */
  HttpResultWrapper<UserDto> register(UserDto userDto);

  /**
   * login
   * @param userDto user info
   * @return
   */
  HttpResultWrapper<UserDto> login(UserDto userDto);

  /**
   * update user
   * @param userDto user dto
   * @return true or false
   */
  HttpResultWrapper<UserDto> update(UserDto userDto);

  /**
   * query users by ids
   * @param ids collection of user id
   * @return list of user
   */
  HttpResultWrapper<List<UserDto>> queryUsers(Collection<String> ids);
}
