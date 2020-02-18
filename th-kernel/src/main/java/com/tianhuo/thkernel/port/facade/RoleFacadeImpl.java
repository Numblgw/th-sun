package com.tianhuo.thkernel.port.facade;

import com.alibaba.dubbo.config.annotation.Service;
import com.tianhuo.sunshine.dto.RoleDto;
import com.tianhuo.sunshine.service.RoleFacade;
import com.tianhuo.thcommon.dto.HttpResultWrapper;
import com.tianhuo.thkernel.domain.role.RoleService;
import com.tianhuo.thkernel.domain.role.repository.RoleConverter;


import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.List;

/**
 * impl
 * @author liguowei
 * @date 2020-02-18 20:08:16
 */
@Service
@Component
public class RoleFacadeImpl implements RoleFacade {

  @Resource
  private RoleService roleService;

  @Override
  public HttpResultWrapper<List<RoleDto>> list() {
    return new HttpResultWrapper<List<RoleDto>>()
        .success(RoleConverter.toDto(roleService.list()));
  }

  @Override
  public HttpResultWrapper<Boolean> add(RoleDto roleDto) {
    roleService.add(RoleConverter.convert(roleDto));
    return new HttpResultWrapper<Boolean>().success(Boolean.TRUE);
  }

  @Override
  public HttpResultWrapper<Boolean> update(RoleDto roleDto) {
    roleService.update(RoleConverter.convert(roleDto));
    return new HttpResultWrapper<Boolean>().success(Boolean.TRUE);
  }

  @Override
  public HttpResultWrapper<Boolean> delete(String id) {
    roleService.delete(id);
    return new HttpResultWrapper<Boolean>().success(Boolean.TRUE);
  }
}
