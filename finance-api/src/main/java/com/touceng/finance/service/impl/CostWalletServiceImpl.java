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
import com.touceng.domain.entity.finance.CostWallet;
import com.touceng.finance.mapper.CostWalletMapper;
import com.touceng.finance.service.ICostWalletService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 内部成本钱包账户 服务实现类
 * </p>
 *
 * @author Wu,Hua-Zheng
 * @since 2018-08-27
 */
@Slf4j
@Service
@Transactional
public class CostWalletServiceImpl extends BaseServiceImpl<CostWallet> implements ICostWalletService {

	@Autowired
	private CostWalletMapper costWalletMapper;

	@Override
	public void syncCostdata(List<CostWallet> costVOlist) {
		for(CostWallet temp:costVOlist) {
			super.save(temp);
		}
		
	}
	
	@Override
	public CostWallet createCostWallet(CostWallet costWallet) {
		// 账户号生成
		costWallet.setAccountNo(super.snowFlake.nextId());
		costWallet.setStatus(TouCengConstant.ONE);
		super.save(costWallet);
		log.info("【成本账户Service】-success-创建成本账户成功：{}",JSON.toJSONString(costWallet));
		return costWallet;
	}

	@Override
	public CostWallet getCostWallet(String currency, String channelAccountNo) {
		CostWallet costWallet =  super.getAccountByExample("channelAccountNo",channelAccountNo,currency,CostWallet.class);
		if (null == costWallet) {
			costWallet = new CostWallet();
			costWallet.setChannelAccountNo(channelAccountNo);
			costWallet.setCurrency(currency);
			log.info("【成本账户Service】-info-成本账户为空，准备创建成本账户,参数：{}",JSON.toJSONString(costWallet));
			// 内部成本钱包账户为空，创建内部成本钱包账户
			costWallet = this.createCostWallet(costWallet);
		}

		// 账户校验
		if (TouCengConstant.ONE != costWallet.getStatus().intValue()) {
			log.info("【成本账户Service】-error-成本账户状态错误：{}",JSON.toJSONString(costWallet));
			throw new BusinessException("【成本账户Service】成本账户处于非正常状态");
		}

		log.info("【成本账户Service】-info-获取成本账户信息：{}",JSON.toJSONString(costWallet));
		
		return costWallet;
	}

	@Override
	public boolean updateCostWallet(String channelAccountNo, String currency, BigDecimal amount, BigDecimal bizType) {
		amount = amount.multiply(bizType);
		boolean result = costWalletMapper.updateWallet(channelAccountNo, currency, amount) == TouCengConstant.ONE;
		log.info("【成本账户Service】-info-修改成本账户,参数,channelAccountNo:{},currency:{},amount:{},结果:{}", channelAccountNo, currency, amount, result);
		if (!result) {
			throw new BusinessException("【成本账户Service】，成本账户可用余额不足");
		}
		return result;
	}

	/** 
	* @Description: 账户成本查询
	* @Param: [listPageDTO] 
	* @return: com.touceng.common.base.PageInfo<com.touceng.domain.entity.finance.CostWallet> 
	* @Author: yangxu 
	* @Date: 2018/9/12 
	*/
	@Override
	public PageInfo<CostWallet> pageCostWallet(ListPageDTO listPageDTO) {
		return new PageInfo<CostWallet>(costWalletMapper.pageCostWallet(listPageDTO));
	}


}
