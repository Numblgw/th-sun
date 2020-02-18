package com.tianhuo.sunshine.service;

import com.tianhuo.sunshine.dto.CategoryDto;
import com.tianhuo.sunshine.dto.RoleDto;
import com.tianhuo.thcommon.dto.HttpResultWrapper;

import java.util.List;

/**
 * role facade
 * @author liguowei
 * @date 2020-02-18 20:04:49
 */
public interface RoleFacade {

  /**
   * role dto list
   * @return list of role dto
   */
  HttpResultWrapper<List<RoleDto>> list();

  /**
   * add role
   * @param roleDto role dto
   * @return success true
   */
  HttpResultWrapper<Boolean> add(RoleDto roleDto);

  /**
   * modify role
   * @param roleDto role dto
   * @return success true
   */
  HttpResultWrapper<Boolean> update(RoleDto roleDto);

  /**
   * delete role
   * @param id role id
   * @return success true
   */
  HttpResultWrapper<Boolean> delete(String id);
}
