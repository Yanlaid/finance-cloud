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
 * 代理商钱包账户记录日志
 * </p>
 *
 * @author Wu, Hua-Zheng
 * @since 2018-08-27
 */
@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "tc_agent_wallet_log")
public class AgentWalletLog extends BaseEntity {

	private static final long serialVersionUID = 1L;


	/**
	 * 代理商主键id
	 */
	@Column(name = "AGENT_ID")
	private String agentId;

	/**
	 * 用户主键id
	 */
	@Column(name = "USER_ID")
	private String userId;

	/**
	 * 代理账户编号
	 */
	@Column(name = "AGENT_WALLET_ACCOUNT_NO")
	private String agentWalletNo;
	/**
	 * 多宝订单金额
	 */
	@Column(name = "ORDER_AMOUNT")
	private BigDecimal orderAmount;
	
	/**
	 * 多宝订单类型[0-收款；1-付款]
	 */
	@Column(name = "ORDER_TYPE")
	private Integer orderType;

	/**
	 * 多宝订单号
	 */
	@Column(name = "ORDER_NO")
	private String orderNo;
	/**
	 * 收入
	 */
	@Column(name = "INCOME")
	private BigDecimal income = BigDecimal.ZERO;
	/**
	 * 支出
	 */
	@Column(name = "OUTCOME")
	private BigDecimal outcome = BigDecimal.ZERO;
	/**
	 * 余额
	 */
	@Column(name = "BALANCE")
	private BigDecimal balance = BigDecimal.ZERO;

	/**
	 * 货币类型[RMB-人民币]
	 */
	@Column(name = "CURRENCY")
	private String currency = ECurrencyEnum.RMB.getCode();

	/**
	 * 业务类型
	 */
	@Column(name = "PRODUCT_CODE")
	private String productCode;

	/**
	 * 业务类型名称
	 */
	@Column(name = "PRODUCT_NAME")
	private String productName;

	/**
	 * 状态[0-待处理；1-成功；2-失败；3-异常]
	 */
	@Column(name = "STATUS")
	private Integer status=TouCengConstant.ONE;

	/**
	 * 修改时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "UPDATE_TIME")
	private Date updateTime;

}
