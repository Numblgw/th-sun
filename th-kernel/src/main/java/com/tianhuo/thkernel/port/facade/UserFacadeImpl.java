package com.tianhuo.thkernel.port.facade;

import com.alibaba.dubbo.config.annotation.Service;
import com.tianhuo.sunshine.dto.UserDto;
import com.tianhuo.sunshine.service.UserFacade;
import com.tianhuo.thcommon.domain.User;
import com.tianhuo.thcommon.dto.HttpResultWrapper;
import com.tianhuo.thcommon.enums.HttpResultStatus;
import com.tianhuo.thcommon.utils.StringUtil;
import com.tianhuo.thkernel.domain.article.ArticleService;
import com.tianhuo.thkernel.domain.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

/**
 * user rpc service impl
 * @see com.tianhuo.sunshine.service.UserFacade
 * @author liguowei
 * @date 2019-12-07 22:40:33
 */
@Service
public class UserFacadeImpl implements UserFacade {

  @Autowired
  private UserService userService;

  @Autowired
  private ArticleService articleService;

  @Override
  public HttpResultWrapper<UserDto> queryById(String id) {
    User user = userService.getById(id);
    if(null == user) {
      return new HttpResultWrapper<UserDto>().fail(HttpResultStatus.INVALID_PARAM);
    }
    Integer articleCount = articleService.countByUserId(StringUtil.convertToLong(id, 0L));
    LocalDateTime localDateTime = articleService.getLastPublishingDateByUser(StringUtil.convertToLong(id, 0L));
    return new HttpResultWrapper<UserDto>().success(assemble(user, articleCount, localDateTime));
  }

  @Override
  public HttpResultWrapper<UserDto> queryByUsername(String username) {
    User user = userService.getByUsername(username);
    if(null == user) {
      return new HttpResultWrapper<UserDto>().fail(HttpResultStatus.INVALID_PARAM);
    }
    Integer articleCount = articleService.countByUserId(StringUtil.convertToLong(user.getId(), 0L));
    LocalDateTime localDateTime = articleService.getLastPublishingDateByUser(StringUtil.convertToLong(user.getId(), 0L));
    return new HttpResultWrapper<UserDto>().success(assemble(user, articleCount, localDateTime));
  }

  @Override
  public HttpResultWrapper<List<UserDto>> queryAdminList() {
    return null;
  }

  @Override
  public HttpResultWrapper addUser(UserDto userDto) {
    if(userDto == null) {
      return new HttpResultWrapper().fail(HttpResultStatus.INVALID_PARAM);
    }
    userService.add(convert(userDto));
    return new HttpResultWrapper().success();
  }

  private UserDto assemble(User user, Integer articleCount, LocalDateTime lastPublishingDate) {
    return UserDto.builder()
        .id(user.getId())
        .username(user.getUsername())
        .password(user.getPassword())
        .nickname(user.getNickname())
        .registeredTime(user.getRegisteredTime())
        .roleId(user.getRoleId())
        .articleCount(articleCount)
        .lastPublishDate(lastPublishingDate)
        .build();
  }

  private User convert(UserDto userDto) {
    if(userDto == null) {
      return null;
    }
    return new User(
        userDto.getId(),
        userDto.getUsername(),
        userDto.getPassword(),
        userDto.getNickname(),
        userDto.getRegisteredTime(),
        userDto.getRoleId()
    );
  }
}
