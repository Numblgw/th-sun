package com.tianhuo.thkernel.port.facade;

import com.alibaba.dubbo.config.annotation.Service;
import com.tianhuo.sunshine.dto.CategoryDto;
import com.tianhuo.sunshine.service.CategoryFacade;
import com.tianhuo.thcommon.dto.HttpResultWrapper;
import com.tianhuo.thkernel.application.UserApplicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author liguowei
 * @date 2019-12-22 15:36:01
 */
@Service
@Component
public class CategoryFacadeImpl implements CategoryFacade {

  @Autowired
  private UserApplicationService userApplicationService;

  @Override
  public HttpResultWrapper<List<CategoryDto>> list() {
    return new HttpResultWrapper<List<CategoryDto>>()
        .success(userApplicationService.categoryList());
  }
}
