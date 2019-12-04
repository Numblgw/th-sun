package com.tianhuo.thkernel.domain.role;

import com.tianhuo.thcommon.domain.Role;

import java.util.List;

/**
 * role service
 * @author liguowei
 * @date 2019-12-03 17:50:41
 */
public interface RoleService {

  /**
   * get role list
   * @return role domain object list
   */
  List<Role> list();

  /**
   * add a role
   * @param role role domain object to add
   */
  void add(Role role);

  /**
   * update one role
   * only can update name or description
   * @param role role domain to update
   */
  void update(Role role);

  /**
   * get role by role id
   * @param id role id
   * @return role info
   */
  Role getById(String id);
}
