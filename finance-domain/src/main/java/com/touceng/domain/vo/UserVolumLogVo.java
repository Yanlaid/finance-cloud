package com.touceng.domain.vo;/**
 * Created by tc_dev on 2018/9/11.
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: finance
 * @description: 商户交易量返回实体
 * @author: yang.xu
 * @create: 2018-09-11 19:49
 **/

@Data
@ApiModel(value = "商户交易量返回实体", description = "商户交易量返回实体")
public class UserVolumLogVo {

    @ApiModelProperty(value = "交易日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime; //交易日期

    @ApiModelProperty(value = " 商户号")
    private String userCode;


    @ApiModelProperty(value = "产品名称")
    private String productName;


    @ApiModelProperty(value = "笔数")
    private Integer totalCount;

    @ApiModelProperty(value = "金额")
    private BigDecimal totalAmount= BigDecimal.ZERO;

    @ApiModelProperty(value = "净利")
    private BigDecimal totalProfit = BigDecimal.ZERO;

}
