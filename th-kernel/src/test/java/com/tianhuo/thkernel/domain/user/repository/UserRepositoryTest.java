package com.tianhuo.thkernel.domain.user.repository;


import com.tianhuo.thkernel.ThKernelApplication;
import com.tianhuo.thkernel.domain.user.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.time.LocalDateTime;

@SpringBootTest(classes = ThKernelApplication.class)
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

  @Resource
  private RedisTemplate<String, User> userRedisTemplate;

  @Test
  public void testRedisSerializer() {
    User user = new User("1", "2", "3", "4", LocalDateTime.now(), "5");
    userRedisTemplate.opsForValue().set("test-user", user);
    System.out.println(userRedisTemplate.opsForValue().get("test-user"));
  }
}