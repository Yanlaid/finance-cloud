package com.touceng.finance.service;

import java.math.BigDecimal;
import java.util.List;

import com.touceng.common.base.BaseService;
import com.touceng.common.base.PageInfo;
import com.touceng.domain.dto.query.ListPageDTO;
import com.touceng.domain.entity.finance.UserWallet;

/**
 * <p>
 * 会员钱包账户 服务类
 * </p>
 *
 * @author Wu, Hua-Zheng
 * @since 2018-08-27
 */
public interface IUserWalletService extends BaseService<UserWallet> {

    /**
     * @methodDesc: 方法描述: 同步用户数据
     * @author Wu, Hua-Zheng
     * @createTime 2018年8月27日 下午9:22:47
     * @version v1.0.0
     */
    void syncUserdata(List<UserWallet> userVOlist);


    /**
     * @methodDesc: 方法描述: 创建商户账户
     * @author Wu,Hua-Zheng
     * @createTime 2018年9月8日 下午10:48:54
     * @version v1.0.0
     * @param userWallet
     */
    UserWallet createUserWallet(UserWallet userWallet);


   
	/**
	 * @methodDesc: 方法描述: 获取账户详情
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月8日 下午10:49:17
	 * @version v1.0.0
	 * @param userId
	 * @param currency
	 * @param userType
	 * @param userCode
	 * @param agentId
	 */
    UserWallet getUserWallet(String userId,String currency,Integer userType,String userCode, String agentId);


    /**
     * @methodDesc: 方法描述: 修改商户账户余额
     * @author Wu,Hua-Zheng
     * @createTime 2018年9月8日 下午10:49:43
     * @version v1.0.0
     * @param userId
     * @param currency
     * @param amount
     * @param bizType
     * @return
     */
    boolean updateUserWallet(String userId, String currency, BigDecimal amount,BigDecimal bizType);

    /** 
    * @Description: 商户账户查询
    * @Param: [listPageDTO] 
    * @return: com.touceng.common.base.PageInfo<com.touceng.domain.entity.finance.UserWallet> 
    * @Author: yangxu 
    * @Date: 2018/9/12 
    */
    PageInfo<UserWallet> pageUserWallet(ListPageDTO listPageDTO);

    
    BigDecimal getUserWalletBelence(String userCode, String currency);


}
