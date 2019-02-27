package com.touceng.finance.service;

import java.math.BigDecimal;
import java.util.List;

import com.touceng.common.base.BaseService;
import com.touceng.common.base.PageInfo;
import com.touceng.domain.dto.query.ListPageDTO;
import com.touceng.domain.entity.finance.CostWallet;

/**
 * <p>
 * 内部成本钱包账户 服务类
 * </p>
 *
 * @author Wu,Hua-Zheng
 * @since 2018-08-27
 */
public interface ICostWalletService extends BaseService<CostWallet> {

	/**
	 * @methodDesc: 方法描述: 同步成本账户数据
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月8日 下午11:58:16
	 * @version v1.0.0
	 * @param costVOlist
	 */
	public void syncCostdata(List<CostWallet> costVOlist);

	/**
	 * @methodDesc: 方法描述: 创建成本账户
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月8日 下午11:58:29
	 * @version v1.0.0
	 * @param costWallet
	 * @return
	 */
	CostWallet createCostWallet(CostWallet costWallet);

	/**
	 * @methodDesc: 方法描述: 获取成本账户信息
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月8日 下午11:58:45
	 * @version v1.0.0
	 * @param currency
	 * @param channelAccountNo
	 * @return
	 */
	CostWallet getCostWallet(String currency, String channelAccountNo);

	/**
	 * @methodDesc: 方法描述: 修改成本账户余额
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月8日 下午11:59:01
	 * @version v1.0.0
	 * @param channelAccountNo
	 * @param currency
	 * @param amount
	 * @param bizType
	 * @return
	 */
	boolean updateCostWallet(String channelAccountNo, String currency, BigDecimal amount, BigDecimal bizType);

	/** 
	* @Description: 账户成本查询 
	* @Param: [listPageDTO] 
	* @return: com.touceng.common.base.PageInfo<com.touceng.domain.entity.finance.CostWallet> 
	* @Author: yangxu 
	* @Date: 2018/9/12 
	*/
	PageInfo<CostWallet> pageCostWallet(ListPageDTO listPageDTO);
}
