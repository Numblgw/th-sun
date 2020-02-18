package com.tianhuo.thkernel.port.facade;

import com.alibaba.dubbo.config.annotation.Service;
import com.tianhuo.sunshine.dto.CategoryDto;
import com.tianhuo.sunshine.service.CategoryFacade;
import com.tianhuo.thcommon.dto.HttpResultWrapper;
import com.tianhuo.thcommon.utils.StringUtil;
import com.tianhuo.thkernel.application.UserApplicationService;
import com.tianhuo.thkernel.domain.category.CategoryService;
import com.tianhuo.thkernel.domain.category.repository.CategoryConverter;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.List;

/**
 * @author liguowei
 * @date 2019-12-22 15:36:01
 */
@Service
@Component
public class CategoryFacadeImpl implements CategoryFacade {

  @Resource
  private UserApplicationService userApplicationService;

  @Resource
  private CategoryService categoryService;

  @Override
  public HttpResultWrapper<List<CategoryDto>> list() {
    return new HttpResultWrapper<List<CategoryDto>>()
        .success(userApplicationService.categoryList());
  }

  @Override
  public HttpResultWrapper<Boolean> add(CategoryDto categoryDto) {
    categoryService.add(CategoryConverter.convert(categoryDto));
    return new HttpResultWrapper<Boolean>().success(Boolean.TRUE);
  }

  @Override
  public HttpResultWrapper<Boolean> update(CategoryDto categoryDto) {
    categoryService.update(CategoryConverter.convert(categoryDto));
    return new HttpResultWrapper<Boolean>().success(Boolean.TRUE);
  }

  @Override
  public HttpResultWrapper<Boolean> delete(String id) {
    categoryService.delete(StringUtil.convertToLong(id, 0L).intValue());
    return new HttpResultWrapper<Boolean>().success(Boolean.TRUE);
  }
}
