package com.tianhuo.thkernel.Infrastructure.config;

import com.tianhuo.thcommon.domain.Article;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.net.UnknownHostException;

/**
 * @author liguowei
 * @date 2019-12-01 12:26:44
 */
@Configuration
public class RedisConfig {

  @Bean
  RedisTemplate<String, Article> articleRedisTemplate(RedisConnectionFactory redisConnectionFactory)
      throws UnknownHostException {
    RedisTemplate redisTemplate = new RedisTemplate();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    return redisTemplate;
  }
}
