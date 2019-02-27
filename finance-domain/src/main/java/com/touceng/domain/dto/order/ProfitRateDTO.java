package com.touceng.domain.dto.order;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @classDesc: 类描述: 手续费参数配置
 * @author Wu,Hua-Zheng
 * @createTime 2018年8月29日 下午2:24:11
 * @version v1.0.0
 * @copyright: 上海投嶒网络技术有限公司
 */
@Data
public class ProfitRateDTO implements Serializable {

	private static final long serialVersionUID = -4665417590331521667L;

	@NotNull(message = "费用类型【feeType】不可为空")
	private Integer feeType;// 费用类型 number 费用类型-feeType【0-费率；1-固定额】

	@NotNull(message = "代理底价【floorAgentProfit】不可为空")
	private Integer floorAgentProfit;// 代理底价 number

	@NotNull(message = "成本底价【floorCostProfit】不可为空")
	private Integer floorCostProfit;// 渠道产品成本底价 number

	@NotNull(message = "零售费【retailProfit】不可为空")
	private Integer retailProfit;// 零售费 number

}
