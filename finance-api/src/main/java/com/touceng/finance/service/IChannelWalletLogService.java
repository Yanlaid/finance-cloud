package com.touceng.finance.service;

import java.math.BigDecimal;
import java.util.List;

import com.touceng.common.base.BaseService;
import com.touceng.common.base.PageInfo;
import com.touceng.domain.dto.query.ListPageDTO;
import com.touceng.domain.entity.finance.ChannelWalletLog;

/**
 * <p>
 * 内部渠道钱包账户记录日志 服务类
 * </p>
 *
 * @author Wu,Hua-Zheng
 * @since 2018-08-27
 */
public interface IChannelWalletLogService extends BaseService<ChannelWalletLog> {

	/**
	 * @methodDesc: 方法描述: 保存内部渠道账户流水
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月8日 下午11:30:39
	 * @version v1.0.0
	 * @param channelWalletLog
	 * @return
	 */
	ChannelWalletLog saveChannelWalletLog(ChannelWalletLog channelWalletLog);

	/**
	 * @methodDesc: 方法描述: 付款失败，修改内部渠道账户流水状态
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月8日 下午11:30:58
	 * @version v1.0.0
	 * @param orderNo
	 * @param currency
	 * @param status
	 * @return
	 */
	int updateChannelWalletLog(String orderNo, String currency, int status);

	/**
	 * @methodDesc: 方法描述: 付款失败，获取需要还原的金额
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月8日 下午11:31:31
	 * @version v1.0.0
	 * @param orderNo
	 * @param currency
	 * @param status
	 * @return
	 */
	BigDecimal getAmountByParams(String orderNo, String currency, int status);

	public List<String> getChannelAccountNo(String orderNo, String currency, int status);

	/** 
	* @Description: 渠道手续费流水
	* @Param: [listPageDTO] 
	* @return: com.touceng.common.base.PageInfo<com.touceng.domain.entity.finance.ChannelWalletLog> 
	* @Author: yangxu 
	* @Date: 2018/9/12 
	*/
	PageInfo<ChannelWalletLog> pageChannelWallet(ListPageDTO listPageDTO);

}
