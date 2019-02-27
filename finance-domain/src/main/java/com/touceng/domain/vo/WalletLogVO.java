package com.touceng.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.touceng.common.constant.TouCengConstant;
import com.touceng.domain.enums.ECurrencyEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 钱包账户记录日志
 * </p>
 *
 * @author Wu, Hua-Zheng
 * @since 2018-08-27
 */
@Data
@Entity
@ApiModel(value = "钱包账户流水", description = "钱包账户流水")
public class WalletLogVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 商户类型：0 普通商户，1 代理商户，2 子商户
     */
    @Column(name = "user_type")
    public Integer userType;

    /**
     * 多宝订单金额
     */
    @ApiModelProperty(value = " 订单金额")
    @Column(name = "order_amount")
    private BigDecimal orderAmount;

    /**
     * 多宝订单类型[0-收款；1-付款]
     */
    @Column(name = "order_type")
    private Integer orderType;


    @ApiModelProperty(value = " 订单类型")
    private String orderTypeName;

    public String getOrderTypeName() {
        if(null == orderType){
            return null;
        }
        return (orderType == 0 ? "收款" : "付款");
    }

    @ApiModelProperty(value = " 商户号")
    private String userCode;

    /**
     * 多宝订单号
     */
    @ApiModelProperty(value = " 订单号")
    private String orderNo;
    /**
     * 收入
     */
    @ApiModelProperty(value = " 收入")
    private BigDecimal income;
    /**
     * 支出
     */
    @ApiModelProperty(value = " 支出")
    private BigDecimal outcome;
    /**
     * 余额
     */
    @ApiModelProperty(value = " 余额")
    private BigDecimal balance;
    /**
     * 货币类型[RMB-人民币]
     */
    private String currency;

    /**
     * 业务类型
     */
    private String productCode;

    /**
     * 业务类型名称
     */
    @ApiModelProperty(value = " 业务类型")
    private String productName;

    /**
     * 状态[0-待处理；1-成功；2-失败；3-异常]
     */
    @Column(name = "status")
    private Integer status;

    @ApiModelProperty(value = " 状态")
    private String statusName;

    public String getStatusName() {
        if(null == status){
            return null;
        }
        return (status == 1 ? "成功" : "失败");
    }

    // 创建时间
    @ApiModelProperty(value = "交易时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


}
