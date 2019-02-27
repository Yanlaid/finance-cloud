package com.touceng.domain.entity.finance;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.touceng.common.base.BaseEntity;
import com.touceng.common.constant.TouCengConstant;
import com.touceng.domain.enums.ECurrencyEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 会员钱包账户记录日志
 * </p>
 *
 * @author Wu, Hua-Zheng
 * @since 2018-08-27
 */
@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "会员钱包账户流水", description = "会员钱包账户流水")
@Table(name = "tc_user_wallet_log")
public class UserWalletLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户主键id
     */
    @ApiModelProperty(value = " 主键")
    @Column(name = "user_id")
    private String userId;

    /**
     * 用户商户号
     */
    @ApiModelProperty(value = " 商户号")
    @Column(name = "user_code")
    private String userCode;

    /**
     * 商户类型：0 普通商户，1 代理商户，2 子商户
     */
    @ApiModelProperty(value = " 商户类型")
    @Column(name = "user_type")
    public Integer userType;

    /**
     * 用户账户编号
     */
    @Column(name = "user_wallet_account_no")
    private String userWalletNo;
    /**
     * 多宝订单金额
     */
    @ApiModelProperty(value = " 订单金额")
    @Column(name = "order_amount")
    private BigDecimal orderAmount;

    /**
     * 多宝订单类型[0-收款；1-付款]
     */
    @ApiModelProperty(value = " 订单类型[0-收款；1-付款]")
    @Column(name = "order_type")
    private Integer orderType;

    /**
     * 多宝订单号
     */
    @ApiModelProperty(value = " 订单号")
    @Column(name = "order_no")
    private String orderNo;
    /**
     * 收入
     */
    @ApiModelProperty(value = " 收入")
    @Column(name = "income")
    private BigDecimal income = BigDecimal.ZERO;
    /**
     * 支出
     */
    @ApiModelProperty(value = " 支出")
    @Column(name = "outcome")
    private BigDecimal outcome = BigDecimal.ZERO;
    /**
     * 余额
     */
    @ApiModelProperty(value = " 余额")
    @Column(name = "balance")
    private BigDecimal balance = BigDecimal.ZERO;
    /**
     * 货币类型[RMB-人民币]
     */
    @ApiModelProperty(value = " 货币类型")
    @Column(name = "currency")
    private String currency = ECurrencyEnum.RMB.getCode();

    /**
     * 业务类型
     */
    @Column(name = "product_code")
    private String productCode;

    /**
     * 业务类型名称
     */
    @ApiModelProperty(value = " 业务类型")
    @Column(name = "product_name")
    private String productName;

    /**
     * 状态[0-待处理；1-成功；2-失败；3-异常]
     */
    @ApiModelProperty(value = " 状态")
    @Column(name = "status")
    private Integer status = TouCengConstant.ONE;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "update_time")
    private Date updateTime;

}
