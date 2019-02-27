package com.touceng.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by tc_dev on 2018/9/11.
 */
@Data
@ApiModel(value = "分润日报表", description = "分润日报表")
public class ShareProfitVO implements Serializable{

    @ApiModelProperty(value = "交易日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime; //交易日期

    @ApiModelProperty(value = "交易笔数")
    private Integer count;//交易笔数
    @ApiModelProperty(value = "交易金额")
    private BigDecimal amount;//交易金额
    @ApiModelProperty(value = "分润")
    private BigDecimal outAmount;//分润金额

}
