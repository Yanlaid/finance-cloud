package com.touceng.finance.service;

import java.math.BigDecimal;
import java.util.List;

import com.touceng.common.base.BaseService;
import com.touceng.common.base.PageInfo;
import com.touceng.domain.dto.query.ListPageDTO;
import com.touceng.domain.entity.finance.ChargeWalletLog;

/**
 * <p>
 * 手续费钱包账户记录日志 服务类
 * </p>
 *
 * @author Wu,Hua-Zheng
 * @since 2018-08-27
 */
public interface IChargeWalletLogService extends BaseService<ChargeWalletLog> {

	/**
	 * @methodDesc: 方法描述: 保存内部手续费账户流水
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月8日 下午11:52:44
	 * @version v1.0.0
	 * @param chargeWalletLog
	 * @return
	 */
	ChargeWalletLog saveChargeWalletLog(ChargeWalletLog chargeWalletLog);

	/**
	 * @methodDesc: 方法描述: 订单支付失败，修改手续费账户流水状态
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月8日 下午11:53:19
	 * @version v1.0.0
	 * @param orderNo
	 * @param currency
	 * @param status
	 * @return
	 */
	int updateChargeWalletLog(String orderNo, String currency, int status);

	/**
	 * @methodDesc: 方法描述: 订单支付失败，获取需要退换的手续费账户金额
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月8日 下午11:54:00
	 * @version v1.0.0
	 * @param orderNo
	 * @param currency
	 * @param status
	 * @return
	 */
	BigDecimal getAmountByParams(String orderNo, String currency, int status);

	/**
	 * @methodDesc: 方法描述: 获取渠道账户号
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月8日 下午11:54:37
	 * @version v1.0.0
	 * @param orderNo
	 * @param currency
	 * @param status
	 * @return
	 */
	public List<String> getChannelAccountNo(String orderNo, String currency, int status);

	/** 
	* @Description: 账户手续费流水查询
	* @Param: [listPageDTO] 
	* @return: com.touceng.common.base.PageInfo<com.touceng.domain.entity.finance.ChargeWalletLog> 
	* @Author: yangxu 
	* @Date: 2018/9/12 
	*/
	PageInfo<ChargeWalletLog> pageChargeWallet(ListPageDTO listPageDTO);
}
