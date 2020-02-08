package com.tianhuo.sunshine.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * category dto
 * @author liguowei
 * @date 2019-12-22 15:24:09
 */
@Data
@Builder
public class CategoryDto implements Serializable {

  private static final long serialVersionUID = 8920792500315536426L;

  private String id;

  private String name;
}
