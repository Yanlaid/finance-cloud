package com.touceng.domain.vo;/**
 * Created by tc_dev on 2018/9/11.
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: finance
 * @description: 商户交易量
 * @author: yang.xu
 * @create: 2018-09-11 17:38
 **/

@Data
@ApiModel(value = "商户交易量报表", description = "商户交易量报表")
public class UserVolumVo implements Serializable {

    @ApiModelProperty(value = "id")
    private String id; //交易日期

    @ApiModelProperty(value = "交易日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime; //交易日期

    @ApiModelProperty(value = " 商户号")
    private String userCode;

    @ApiModelProperty(value = "订单类型")
    private Integer orderType;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "手续费")
    private BigDecimal chargeAmount= BigDecimal.ZERO;

    @ApiModelProperty(value = "成本")
    private BigDecimal costAmount= BigDecimal.ZERO;

    @ApiModelProperty(value = "分润")
    private BigDecimal fenAmount= BigDecimal.ZERO;

    @ApiModelProperty(value = "笔数")
    private Integer totalCount;

    @ApiModelProperty(value = "金额")
    private BigDecimal totalAmount= BigDecimal.ZERO;

    @ApiModelProperty(value = "净利")
    private BigDecimal totalProfit = BigDecimal.ZERO;

}
