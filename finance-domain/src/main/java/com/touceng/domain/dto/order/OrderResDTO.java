package com.touceng.domain.dto.order;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

import com.touceng.domain.enums.ECurrencyEnum;

import lombok.Data;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 类描述: 付款订单回调参数
 * @createTime 2018年8月29日 下午2:26:55
 * @copyright: 上海投嶒网络技术有限公司
 */
@Data
public class OrderResDTO implements Serializable {

    private static final long serialVersionUID = -8726691500312842959L;

    private BigDecimal orderAmount;// 多宝订单金额 string
    @NotBlank(message = "多宝订单编号【orderNo】不可为空")
    private String orderNo;// 多宝订单编号 string
    private String currency=ECurrencyEnum.RMB.getCode();// 货币类型 string RMB

}
