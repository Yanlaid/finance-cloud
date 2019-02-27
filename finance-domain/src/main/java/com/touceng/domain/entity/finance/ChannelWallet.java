package com.touceng.domain.entity.finance;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.touceng.common.base.BaseEntity;
import com.touceng.common.constant.TouCengConstant;
import com.touceng.domain.enums.ECurrencyEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 内部渠道钱包账户
 * </p>
 *
 * @author Wu, Hua-Zheng
 * @since 2018-08-27
 */
@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "tc_channel_wallet")
public class ChannelWallet extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 渠道名称
     */
    @Column(name = "channel_name")
    private String channelName;
    /**
     * 渠道账户号
     */
    @Column(name = "channel_account_no")
    private String channelAccountNo;
    /**
     * 渠道账户名称
     */
    @Column(name = "channel_account_name")
    private String channelAccountName;
    /**
     * 渠道邮箱
     */
    @Column(name = "email")
    private String email;


	
    /**
     * 收入
     */
    @Column(name = "income")
    private BigDecimal income = BigDecimal.ZERO;
    /**
     * 支出
     */
    @Column(name = "outcome")
    private BigDecimal outcome = BigDecimal.ZERO;
    /**
     * 余额
     */
    @Column(name = "balance")
    private BigDecimal balance = BigDecimal.ZERO;

    /**
     * 货币类型[RMB-人民币]
     */
    @Column(name = "currency")
    private String currency=ECurrencyEnum.RMB.getCode();

    /**
     * 账户编号
     */
    @Column(name = "account_no")
    private String accountNo;


    /**
     * 版本号
     */
    @Column(name = "version")
    private Long version;


    /**
     * 状态[0-禁用；1-正常；2-封存]
     */
    @Column(name = "status")
    private Integer status=TouCengConstant.ONE;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 渠道账户ID
     */
    @Transient
    private String channelAccountId;

}
