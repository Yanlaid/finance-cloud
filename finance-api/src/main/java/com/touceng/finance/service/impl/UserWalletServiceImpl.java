package com.touceng.finance.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.touceng.common.base.BaseServiceImpl;
import com.touceng.common.base.PageInfo;
import com.touceng.common.constant.TouCengConstant;
import com.touceng.common.exception.BusinessException;
import com.touceng.domain.dto.query.ListPageDTO;
import com.touceng.domain.entity.finance.UserWallet;
import com.touceng.finance.mapper.UserWalletMapper;
import com.touceng.finance.service.IUserWalletService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 会员钱包账户 服务实现类
 * </p>
 *
 * @author Wu,Hua-Zheng
 * @since 2018-08-27
 */
@Slf4j
@Service
@Transactional
public class UserWalletServiceImpl extends BaseServiceImpl<UserWallet> implements IUserWalletService {

	@Autowired
	private UserWalletMapper userWalletMapper;

	@Override
	public void syncUserdata(List<UserWallet> userVOlist) {
		
		for(UserWallet temp:userVOlist) {
			super.save(temp);
		}
		 
	}

	@Override
	public UserWallet createUserWallet(UserWallet userWallet) {
		// 账户号生成
		userWallet.setAccountNo(super.snowFlake.nextId());
		userWallet.setStatus(TouCengConstant.ONE);
		super.save(userWallet);
		log.info("【商户账户Service】-success-创建商户账户成功：{}",JSON.toJSONString(userWallet));
		return userWallet;
	}

	@Override
	public UserWallet getUserWallet(String userId,String currency,Integer userType,String userCode, String agentId) {

		// 根据用户ID和货币类型
         UserWallet userWallet = super.getAccountByExample("userId",userId,currency,UserWallet.class);
 		if (null == userWallet) {
 			userWallet = new UserWallet();
			userWallet.setUserId(userId);
			userWallet.setUserType(userType);
			userWallet.setCurrency(currency);
			userWallet.setAgentId(agentId);
			userWallet.setUserCode(userCode);
			log.info("【商户账户Service】-info-商户账户为空，准备创建商户账户,参数：{}",JSON.toJSONString(userWallet));
			userWallet = this.createUserWallet(userWallet);
		}

		// 账户校验
		if (null != userWallet && TouCengConstant.ONE != userWallet.getStatus().intValue()) {
			log.info("【商户账户Service】-error-商户账户状态错误：{}",JSON.toJSONString(userWallet));
			throw new BusinessException("【商户账户Service】商户账户处于非正常状态");
		}
		log.info("【商户账户Service】-info-获取商户账户信息：{}",JSON.toJSONString(userWallet));
		return userWallet;
	}

	@Override
	public boolean updateUserWallet(String userId, String currency, BigDecimal amount,BigDecimal bizType) {
		
		amount = amount.multiply(bizType);
		boolean result = userWalletMapper.updateUserWallet(userId, currency, amount) == TouCengConstant.ONE;
		log.info("【商户账户Service】-info-修改商户账户，参数,商户Id:{},currency:{},amount:{},结果:{}",userId,currency,amount,result);
		if(!result) {
			throw new BusinessException("【商户账户Service】，商户账户可用余额不足");
		}
		return result;
	}

	/** 
	* @Description: 商户账户查询
	* @Param: [listPageDTO] 
	* @return: com.touceng.common.base.PageInfo<com.touceng.domain.entity.finance.UserWallet> 
	* @Author: yangxu 
	* @Date: 2018/9/12 
	*/
	@Override
	public PageInfo<UserWallet> pageUserWallet(ListPageDTO listPageDTO) {
		System.out.println(listPageDTO.getStatus());
		return new PageInfo<UserWallet>(userWalletMapper.pageUserWallet(listPageDTO));
	}

	@Override
	public BigDecimal getUserWalletBelence(String userCode, String currency) {
		return userWalletMapper.getUserWallet(userCode,currency);
	}


}
