package com.touceng.domain.dto.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 类描述: 订单参数
 * @createTime 2018年8月29日 下午2:26:55
 * @copyright: 上海投嶒网络技术有限公司
 */
@Data
public class OrderReqDTO implements Serializable {

    private static final long serialVersionUID = -8726691500312842959L;

    private List<AgentShareDTO> agentShareList;// 多代理分润 array<object>

    @NotBlank(message = "渠道账户名称【channelAccountName】不可为空")
    private String channelAccountName;// 渠道账户名称 string

    @NotBlank(message = "渠道账户号【channelAccountNo】不可为空")
    private String channelAccountNo;// 渠道账户号 string

    @NotBlank(message = " 渠道名称【channelName】不可为空")
    private String channelName;// 渠道名称 string

    @NotNull(message = "多宝订单金额【orderAmount】不可为空")
    private BigDecimal orderAmount;// 多宝订单金额 string

    @NotBlank(message = "多宝订单编号【orderNo】不可为空")
    private String orderNo;// 多宝订单编号 string

    @NotBlank(message = "支付产品编码【productCode】不可为空")
    private String productCode;// 支付产品编码 string

    @NotBlank(message = "支付产品名称【productName】不可为空")
    private String productName;// 支付产品名称 string

    @NotNull(message = " 手续费配置【profitRate】不可为空")
    private ProfitRateDTO profitRate;// 手续费配置 object

    private int withdrawType;// 扣款类型 number 付款【内扣-0/外扣-1】-产品通道/运营查询

    private Integer orderSubType=0;//订单类型

}
