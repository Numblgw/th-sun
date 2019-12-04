package com.tianhuo.thkernel.domain.role.impl;

import com.tianhuo.thkernel.domain.role.RoleService;
import com.tianhuo.thkernel.domain.role.repository.RoleRepository;
import com.tianhuo.thcommon.domain.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * role service impl
 * @author liguowei
 * @date 2019-12-03 18:03:51
 */
@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public List<Role> list() {
    return roleRepository.list();
  }

  @Override
  public void add(Role role) {
    if(null == role) {
      return;
    }
    roleRepository.add(role);
  }

  @Override
  public void update(Role role) {
    if(null == role || StringUtils.isEmpty(role.getId())) {
      return;
    }
    roleRepository.update(role);
  }

  @Override
  public Role getById(String id) {
    if(null == id) {
      return null;
    }
    return roleRepository.findById(id);
  }
}
