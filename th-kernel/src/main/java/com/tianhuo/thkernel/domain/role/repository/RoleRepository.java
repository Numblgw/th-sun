package com.tianhuo.thkernel.domain.role.repository;

import com.tianhuo.thkernel.domain.role.Role;
import com.tianhuo.thkernel.port.persistence.dao.mysql.RoleMapper;
import com.tianhuo.thcommon.utils.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import java.util.List;
import java.util.stream.Collectors;

/**
 * role repository
 * @author liguowei
 * @date 2019-12-03 17:54:03
 */
@Repository
public class RoleRepository {

  @Resource
  private RoleMapper roleMapper;

  /**
   * add a role
   * @param role role domain object to save
   */
  public void add(Role role) {
    roleMapper.insert(RoleConverter.convert(role));
  }

  /**
   * update one role
   * @param role role domain object to update
   */
  public void update(Role role) {
    roleMapper.update(RoleConverter.convert(role));
  }

  /**
   * get role list
   * @return role domain object list
   */
  public List<Role> list() {
    return roleMapper.list().stream()
        .map(RoleConverter::convert)
        .collect(Collectors.toList());
  }

  /**
   * get role by role id
   * @param id role id
   * @return role info
   */
  public Role findById(String id) {
    return RoleConverter.convert(
        roleMapper.findById(StringUtil.convertToLong(id, 0).intValue())
    );
  }

  public boolean delete(String id) {
    return roleMapper.delete(StringUtil.convertToLong(id, 0L).intValue()) > 0;
  }
}
