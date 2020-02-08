package com.tianhuo.thkernel.Infrastructure.config;

import com.alibaba.dubbo.common.serialize.fastjson.FastJsonSerialization;
import com.tianhuo.thkernel.domain.article.Article;
import com.tianhuo.thkernel.domain.user.User;
import com.tianhuo.thkernel.port.persistence.entity.ArticleCacheDO;
import com.tianhuo.thkernel.port.persistence.entity.UserCacheDO;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

/**
 * @author liguowei
 * @date 2019-12-01 12:26:44
 */
@Configuration
public class RedisConfig {

  @Bean
  RedisTemplate<String, ArticleCacheDO> articleRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, ArticleCacheDO> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new FastJson2JsonRedisSerializer(ArticleCacheDO.class));
    return redisTemplate;
  }

  @Bean
  RedisTemplate<String, UserCacheDO> userRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, UserCacheDO> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new FastJson2JsonRedisSerializer(UserCacheDO.class));
    return redisTemplate;
  }
}
