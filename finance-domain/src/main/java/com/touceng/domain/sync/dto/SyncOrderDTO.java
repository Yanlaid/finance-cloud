package com.touceng.domain.sync.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @classDesc: 类描述: 系统内部渠道数据同步
 * @author Wu,Hua-Zheng
 * @createTime 2018年8月27日 下午10:22:48
 * @version v1.0.0
 * @copyright: 上海投嶒网络技术有限公司
 */
@Data
public class SyncOrderDTO implements Serializable {

	private static final long serialVersionUID = 6022675630389033712L;
	private String channelId;
	private String channelAccountId;
	/**
	 * 多宝订单类型[0-收款；1-付款]
	 */
	private Integer orderType;
	private String orderNo;
	private String productCode;
	private String productName;
	private String userId;
	private String userCode;
	private BigDecimal orderAmount;
	private BigDecimal costFee = BigDecimal.ZERO;
	private int costFeeType;

	private BigDecimal chargeFee = BigDecimal.ZERO;
	private int chargeFeeType;
	private BigDecimal agentRebate;
	private String userProductId;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

}
