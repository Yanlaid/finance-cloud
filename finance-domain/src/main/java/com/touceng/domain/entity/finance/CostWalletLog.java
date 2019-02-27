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

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 内部成本账户记录日志
 * </p>
 *
 * @author Wu,Hua-Zheng
 * @since 2018-08-27
 */
@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "tc_cost_wallet_log")
public class CostWalletLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 成本账户编号
	 */
	@Column(name = "cost_wallet_account_no")
	private String costWalletNo;


	/**
	 * 渠道账户号
	 */
	@Column(name = "channel_account_no")
	private String channelAccountNo;
	/**
	 * 多宝订单金额
	 */
	@Column(name = "order_amount")
	private BigDecimal orderAmount;
	/**
	 * 多宝订单类型[0-收款；1-付款]
	 */
	@Column(name = "order_type")
	private Integer orderType;
	
	/**
	 * 多宝订单号
	 */
	@Column(name = "order_no")
	private String orderNo;
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
	 * 业务类型
	 */
	@Column(name = "product_code")
	private String productCode;

	/**
	 * 业务类型名称
	 */
	@Column(name = "product_name")
	private String productName;

	/**
	 * 状态[0-待处理；1-成功；2-失败；3-异常]
	 */
	@Column(name = "status")
	private Integer status=TouCengConstant.ONE;

	/**
	 * 修改时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "update_time")
	private Date updateTime;
}
