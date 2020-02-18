package com.tianhuo.thkernel.domain.role.repository;

import com.tianhuo.sunshine.dto.RoleDto;
import com.tianhuo.thkernel.domain.role.Role;
import com.tianhuo.thkernel.port.persistence.entity.RoleDO;
import com.tianhuo.thcommon.utils.StringUtil;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 *      convert
 * Role <---> RoleDO
 * @author liguowei
 * @date 2019-12-03 17:57:32
 */
public class RoleConverter {

  /**
   * RoleDO to Role
   * @param roleEntity role data object
   * @return role domain object
   */
  public static Role convert(RoleDO roleEntity) {
    return new Role(
        String.valueOf(roleEntity.getId()),
        roleEntity.getName(),
        roleEntity.getDescription()
    );
  }

  /**
   * Role to RoleDO
   * @param role role domain object
   * @return role data object
   */
  public static RoleDO convert(Role role) {
    RoleDO roleEntity = new RoleDO();
    roleEntity.setId(
        StringUtil.convertToLong(role.getId(), 0).intValue()
    );
    roleEntity.setName(role.getName());
    roleEntity.setDescription(role.getDescription());
    return roleEntity;
  }

  public static RoleDto toDto(Role role) {
    return RoleDto.builder()
        .id(role.getId())
        .name(role.getName())
        .description(role.getDescription())
        .build();
  }

  public static List<RoleDto> toDto(Collection<Role> collection) {
    return collection.stream()
        .map(RoleConverter::toDto)
        .collect(Collectors.toList());
  }

  public static Role convert(RoleDto roleDto) {
    return new Role(roleDto.getId(), roleDto.getName(), roleDto.getDescription());
  }
}
