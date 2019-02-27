package com.touceng.finance.service;

import java.math.BigDecimal;
import java.util.List;

import com.touceng.common.base.BaseService;
import com.touceng.common.base.PageInfo;
import com.touceng.domain.dto.query.ListPageDTO;
import com.touceng.domain.entity.finance.ChannelWallet;

/**
 * <p>
 * 内部渠道钱包账户 服务类
 * </p>
 *
 * @author Wu, Hua-Zheng
 * @since 2018-08-27
 */
public interface IChannelWalletService extends BaseService<ChannelWallet> {

	/**
	 * @methodDesc: 方法描述: 同步渠道数据
	 * @author Wu, Hua-Zheng
	 * @createTime 2018年8月27日 下午9:22:47
	 * @version v1.0.0
	 */
	public void syncChanneldata(List<ChannelWallet> channelVOlist);

	/**
	 * @methodDesc: 方法描述: 创建内部渠道账户
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月8日 下午11:51:28
	 * @version v1.0.0
	 * @param channelWallet
	 * @return
	 */
	ChannelWallet createChannelWallet(ChannelWallet channelWallet);

	/**
	 * @methodDesc: 方法描述: 获取内部渠道账户信息
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月8日 下午11:51:54
	 * @version v1.0.0
	 * @param currency
	 * @param channelAccountNo
	 * @param channelAccountName
	 * @param channelName
	 * @return
	 */
	ChannelWallet getChannelWallet(String currency, String channelAccountNo, String channelAccountName,String channelName);

	/**
	 * @methodDesc: 方法描述: 修改内部渠道账户余额
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月8日 下午11:52:14
	 * @version v1.0.0
	 * @param channelAccountNo
	 * @param currency
	 * @param amount
	 * @param bizType
	 * @return
	 */
	boolean updateChannelWallet(String channelAccountNo, String currency, BigDecimal amount, BigDecimal bizType);

	/** 
	* @Description: 渠道手续费查询
	* @Param: [listPageDTO] 
	* @return: com.touceng.common.base.PageInfo<com.touceng.domain.entity.finance.ChannelWallet> 
	* @Author: yangxu 
	* @Date: 2018/9/12 
	*/
	PageInfo<ChannelWallet> pageChannelWallet(ListPageDTO listPageDTO);

}
