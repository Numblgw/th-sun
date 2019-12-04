package com.tianhuo.thkernel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liguowei
 * @date 2019-11-23 12:52:14
 */
@SpringBootApplication
@MapperScan("com.tianhuo.thkernel.port.persistence.dao")
public class ThKernelApplication {

  public static void main(String[] args) {
    SpringApplication.run(ThKernelApplication.class, args);
  }

}
