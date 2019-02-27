package com.touceng.finance.service;

import java.math.BigDecimal;

import com.touceng.common.base.BaseService;
import com.touceng.common.base.PageInfo;
import com.touceng.domain.dto.query.ListPageDTO;
import com.touceng.domain.entity.finance.UserWalletLog;
import com.touceng.domain.vo.WalletLogVO;

/**
 * <p>
 * 会员钱包账户记录日志 服务类
 * </p>
 *
 * @author Wu,Hua-Zheng
 * @since 2018-08-27
 */
public interface IUserWalletLogService extends BaseService<UserWalletLog> {

	/**
	 * @methodDesc: 方法描述: 保存商户余额变动流水
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月8日 下午11:25:04
	 * @version v1.0.0
	 * @param userWalletLog
	 * @return
	 */
    UserWalletLog saveUserWalletLog(UserWalletLog userWalletLog);


    /**
     * @methodDesc: 方法描述: 付款订单失败，修改流水状态
     * @author Wu,Hua-Zheng
     * @createTime 2018年9月8日 下午11:25:35
     * @version v1.0.0
     * @param orderNo
     * @param currency
     * @param status
     * @return
     */
    int updateUserWalletLog(String orderNo, String currency, int status);
    
	
	/**
	 * @methodDesc: 方法描述: 付款订单失败，获取需要还原的金额
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月8日 下午11:26:02
	 * @version v1.0.0
	 * @param orderNo
	 * @param currency
	 * @param status
	 * @return
	 */
	BigDecimal getAmountByParams(String orderNo, String currency, int status);

	/** 
	* @Description: 子商户账户日志查询 
	* @Param: [listPageDTO] 
	* @return: com.touceng.common.base.PageInfo<com.touceng.domain.vo.WalletLogVO> 
	* @Author: yangxu 
	* @Date: 2018/9/12 
	*/
	PageInfo<WalletLogVO> pageSubUserWalletLog(ListPageDTO listPageDTO);

	/** 
	* @Description: 商户账户日志查询 
	* @Param: [listPageDTO] 
	* @return: com.touceng.common.base.PageInfo<com.touceng.domain.vo.WalletLogVO> 
	* @Author: yangxu 
	* @Date: 2018/9/12 
	*/
    PageInfo<WalletLogVO> pageUserWalletLog(ListPageDTO listPageDTO);
    


}
