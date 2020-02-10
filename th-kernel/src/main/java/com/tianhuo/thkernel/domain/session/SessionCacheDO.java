package com.tianhuo.thkernel.domain.session;

import com.tianhuo.thkernel.port.persistence.entity.UserCacheDO;

import lombok.Builder;
import lombok.Data;

/**
 * session cache data object
 * @author liguowei
 * @date 2020-02-10 20:37:48
 */
@Data
@Builder
public class SessionCacheDO {

  private UserCacheDO userCache;

  private Long lifeTime;

  private Long createTime;
}
