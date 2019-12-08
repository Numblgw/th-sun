package com.tianhuo.sunshine.service;

import com.tianhuo.sunshine.dto.UserDto;
import com.tianhuo.thcommon.dto.HttpResultWrapper;

import java.util.List;

/**
 * user rpc service interface
 * @author liguowei
 * @date 2019-12-04 19:56:11
 */
public interface UserFacade {

  /**
   * query user by id
   * @param id user id
   * @return user dto with wrapper
   */
  HttpResultWrapper<UserDto> queryById(String id);

  /**
   * query user by username
   * @param username username
   * @return usr dto with wrapper
   */
  HttpResultWrapper<UserDto> queryByUsername(String username);

  /**
   * get admin user list
   * @return user dto list with wrapper
   */
  HttpResultWrapper<List<UserDto>> queryAdminList();

  /**
   * add one user
   * @param userDto user object to save
   * @return if success return Boolean.TRUE else return Boolean.FALSE
   */
  HttpResultWrapper addUser(UserDto userDto);
}
