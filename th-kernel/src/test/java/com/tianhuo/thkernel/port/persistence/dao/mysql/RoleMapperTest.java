package com.tianhuo.thkernel.port.persistence.dao.mysql;

import com.tianhuo.thkernel.ThKernelApplication;
import com.tianhuo.thkernel.port.persistence.entity.RoleDO;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ThKernelApplication.class)
class RoleMapperTest {

  @Autowired
  private RoleMapper roleMapper;

  @Test
  void insert() {
    RoleDO roleDO = new RoleDO();
    roleDO.setName("xxx");
    roleDO.setDescription("xxx");
    roleMapper.insert(roleDO);
  }

  @Test
  void update() {
    RoleDO roleDO = new RoleDO();
    roleDO.setId(3);
    roleDO.setName("aab");
    roleDO.setDescription("xyx");
    roleMapper.update(roleDO);
  }

  @Test
  void findById() {
    System.out.println(roleMapper.findById(3));
  }

  @Test
  void list() {
    System.out.println(roleMapper.list());
  }
}