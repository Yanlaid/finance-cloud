package com.touceng.domain.dto.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.touceng.domain.enums.ECurrencyEnum;

import lombok.Data;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 类描述: 下单请求参数
 * @createTime 2018年8月29日 下午2:22:27
 * @copyright: 上海投嶒网络技术有限公司
 */
@Data
public class OrderCreateDTO implements Serializable {
	private static final long serialVersionUID = -844180931914541602L;

	private String agentCode;// 代理商商户号 string
	private String agentId;// 代理商ID string

	private String currency = ECurrencyEnum.RMB.getCode();// 货币类型 string RMB

	@NotNull(message = "订单类型【orderType】不可为空")
	private Integer orderType;// 订单类型 number 【收款-0/付款-1（收入/支出）】

	@NotNull(message = "订单列表【orderList】不可为空")
	private List<OrderReqDTO> orderList;// 订单列表 array<object>

	@NotNull(message = "订单总金额【totalAmount】不可为空")
	private BigDecimal totalAmount;// 订单总金额 string 所有订单总额之和

	@NotBlank(message = "商户编号【userCode】不可为空")
	private String userCode;// 用户商户号 string

	@NotBlank(message = "商户ID【userId】不可为空")
	private String userId;// 用户商户ID string
	/**
	 * 商户类型：0 普通商户，1 代理商户，2 子商户
	 */
	@NotNull(message = "商户类型【userType】不可为空")
	public Integer userType;

}
