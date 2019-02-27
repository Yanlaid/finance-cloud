package com.touceng.domain.dto.order;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 类描述: 付款回调请求参数
 * @createTime 2018年8月29日 下午2:22:27
 * @copyright: 上海投嶒网络技术有限公司
 */
@Data
public class OrderNotifyDTO implements Serializable {
    private static final long serialVersionUID = -844180931914541602L;

    @NotNull(message = "订单列表【orderList】不可为空")
    private List<OrderResDTO> orderList;// 订单列表 array<object>

    @NotBlank(message = "商户ID【userId】不可为空")
    private String userId;// 用户商户ID string

    //手动补单
    private Boolean handle = false;// 用户商户ID string

}
