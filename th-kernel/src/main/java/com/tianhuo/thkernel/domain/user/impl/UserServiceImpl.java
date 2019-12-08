package com.tianhuo.thkernel.domain.user.impl;

import com.tianhuo.thkernel.domain.user.User;
import com.tianhuo.thkernel.domain.user.UserService;
import com.tianhuo.thkernel.domain.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * user service
 * @see com.tianhuo.thkernel.domain.user.UserService
 * @author liguowei
 * @date 2019-12-03 16:50:36
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public Boolean add(User user) {
    if(null == user) {
      return Boolean.FALSE;
    }
    return 1 == userRepository.add(user);
  }

  @Override
  public void update(User user) {
    if(null == user) {
      return;
    }
    userRepository.update(user);
  }

  @Override
  public User getById(String id) {
    return userRepository.queryById(id);
  }

  @Override
  public User getByUsername(String username) {
    return userRepository.queryByUsername(username);
  }

  @Override
  public void delete(String id) {
    userRepository.delete(id);
  }
}
