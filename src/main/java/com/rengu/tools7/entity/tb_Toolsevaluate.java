package com.rengu.tools7.entity;

import lombok.Data;

//评价表
@Data
public class tb_Toolsevaluate {
  private String poolId;
  private String evaluateStar;
  private String evaluateContent;
  private String userId;
}
