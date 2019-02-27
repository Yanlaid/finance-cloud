package com.touceng.finance.service;

import com.touceng.domain.dto.order.OrderCreateDTO;
import com.touceng.domain.dto.order.OrderNotifyDTO;

/**
 * @classDesc: 类描述: 付款收款订单账户变化
 * @author Wu,Hua-Zheng
 * @createTime 2018年8月29日 下午3:31:17
 * @version v1.0.0
 * @copyright: 上海投嶒网络技术有限公司
 */
public interface IWalletService {

	/**
	 * @methodDesc: 方法描述: 付款订单创建接口
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年8月29日 下午3:38:41
	 * @version v1.0.0
	 */
	String withdrawCreate(OrderCreateDTO dto);

	/**
	 * @methodDesc: 方法描述: 付款订单回调接口
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年8月29日 下午3:38:41
	 * @version v1.0.0
	 */
	String withdrawNotify(OrderNotifyDTO dto);

	/**
	 * @methodDesc: 方法描述: 收款订单创建接口
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年8月29日 下午3:38:41
	 * @version v1.0.0
	 */
	String payCreate(OrderCreateDTO dto);

}
