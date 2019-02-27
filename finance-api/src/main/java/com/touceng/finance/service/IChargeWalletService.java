package com.touceng.finance.service;

import java.math.BigDecimal;
import java.util.List;

import com.touceng.common.base.BaseService;
import com.touceng.common.base.PageInfo;
import com.touceng.domain.dto.query.ListPageDTO;
import com.touceng.domain.entity.finance.ChargeWallet;

/**
 * <p>
 * 手续费钱包账户 服务类
 * </p>
 *
 * @author Wu, Hua-Zheng
 * @since 2018-08-27
 */
public interface IChargeWalletService extends BaseService<ChargeWallet> {

	/**
	 * @methodDesc: 方法描述: 同步手续费账户数据
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月8日 下午11:55:01
	 * @version v1.0.0
	 * @param chargeVOlist
	 */
	public void syncChargedata(List<ChargeWallet> chargeVOlist);

	/**
	 * @methodDesc: 方法描述: 创建手续费账户
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月8日 下午11:55:19
	 * @version v1.0.0
	 * @param costWallet
	 * @return
	 */
	ChargeWallet createChargeWallet(ChargeWallet costWallet);

	/**
	 * @methodDesc: 方法描述: 查询手续费账户信息
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月8日 下午11:55:36
	 * @version v1.0.0
	 * @param currency
	 * @param channelAccountNo
	 * @return
	 */
	ChargeWallet getChargeWallet(String currency, String channelAccountNo);

	/**
	 * @methodDesc: 方法描述: 修改手续费账户余额
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月8日 下午11:55:52
	 * @version v1.0.0
	 * @param channelAccountNo
	 * @param currency
	 * @param amount
	 * @param bizType
	 * @return
	 */
	boolean updateChargeWallet(String channelAccountNo, String currency, BigDecimal amount, BigDecimal bizType);

	/** 
	* @Description: 账户手续费查询
	* @Param: [listPageDTO] 
	* @return: com.touceng.common.base.PageInfo<com.touceng.domain.entity.finance.ChargeWallet> 
	* @Author: yangxu 
	* @Date: 2018/9/12 
	*/
	PageInfo<ChargeWallet> pageChargeWallet(ListPageDTO listPageDTO);

}
