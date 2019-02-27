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
 * Created by tc_dev on 2018/9/10.
 */
@Data
@ApiModel(value = "渠道报表", description = "渠道报表")
public class ChannelReportVO implements Serializable {

    @ApiModelProperty(value = "id")
    private String id; //交易日期

    @ApiModelProperty(value = "交易日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime; //交易日期
    @ApiModelProperty(value = "账户名称")
    private String channelAccountName;//账户名称
    @ApiModelProperty(value = "交易类型")
    private Integer orderType;//交易类型，0收款1付款
    @ApiModelProperty(value = "渠道名称")
    private String channelName;//渠道名称
    @ApiModelProperty(value = "渠道账户")
    private String channelAccountNo;//渠道账户
    @ApiModelProperty(value = "支付产品")
    private String productName;//支付产品
    @ApiModelProperty(value = "交易笔数")
    private Integer count;//交易笔数
    @ApiModelProperty(value = "交易金额")
    private BigDecimal amount;//交易金额
    @ApiModelProperty(value = "手续费")
    private BigDecimal chargeAmount;//手续费
    @ApiModelProperty(value = "成本")
    private BigDecimal costAmount;//成本
    @ApiModelProperty(value = "分润")
    private BigDecimal outAmount;//分润金额




}
