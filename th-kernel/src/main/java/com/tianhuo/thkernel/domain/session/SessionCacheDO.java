package com.tianhuo.thkernel.domain.session;

import com.tianhuo.thkernel.port.persistence.entity.UserCacheDO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * session cache data object
 * @author liguowei
 * @date 2020-02-10 20:37:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionCacheDO {

  private UserCacheDO userCache;

  private Long lifeTime;

  private Long createTime;
}
