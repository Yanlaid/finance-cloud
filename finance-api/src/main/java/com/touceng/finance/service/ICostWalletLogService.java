package com.touceng.finance.service;

import java.math.BigDecimal;
import java.util.List;

import com.touceng.common.base.BaseService;
import com.touceng.common.base.PageInfo;
import com.touceng.domain.dto.query.ListPageDTO;
import com.touceng.domain.entity.finance.CostWalletLog;

/**
 * <p>
 * 内部成本账户记录日志 服务类
 * </p>
 *
 * @author Wu,Hua-Zheng
 * @since 2018-08-27
 */
public interface ICostWalletLogService extends BaseService<CostWalletLog> {

	/**
	 * @methodDesc: 方法描述: 保存成本流水记录
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月8日 下午11:56:22
	 * @version v1.0.0
	 * @param costWalletLog
	 * @return
	 */
	public CostWalletLog saveCostWalletLog(CostWalletLog costWalletLog);

	/**
	 * @methodDesc: 方法描述: 订单支付失败，修改成本账户流水记录状态
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月8日 下午11:56:47
	 * @version v1.0.0
	 * @param orderNo
	 * @param currency
	 * @param status
	 * @return
	 */
	public int updateCostWalletLog(String orderNo, String currency, int status);

	/**
	 * @methodDesc: 方法描述: 订单支付失败，获取需要退换的金额
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月8日 下午11:57:29
	 * @version v1.0.0
	 * @param orderNo
	 * @param currency
	 * @param status
	 * @return
	 */
	public BigDecimal getAmountByParams(String orderNo, String currency, int status);

	public List<String> getChannelAccountNo(String orderNo, String currency, int status);

	/** 
	* @Description: 账户成本流水查询 
	* @Param: [listPageDTO] 
	* @return: com.touceng.common.base.PageInfo<com.touceng.domain.entity.finance.CostWalletLog> 
	* @Author: yangxu 
	* @Date: 2018/9/12 
	*/
	PageInfo<CostWalletLog> pageCostWallet(ListPageDTO listPageDTO);
}
