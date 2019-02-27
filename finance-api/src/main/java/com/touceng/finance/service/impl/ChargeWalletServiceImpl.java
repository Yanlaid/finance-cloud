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
import com.touceng.domain.entity.finance.ChargeWallet;
import com.touceng.finance.mapper.ChargeWalletMapper;
import com.touceng.finance.service.IChargeWalletService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 手续费钱包账户 服务实现类
 * </p>
 *
 * @author Wu,Hua-Zheng
 * @since 2018-08-27
 */
@Slf4j
@Service
@Transactional
public class ChargeWalletServiceImpl extends BaseServiceImpl<ChargeWallet> implements IChargeWalletService {
	
	@Autowired
    private ChargeWalletMapper chargeWalletMapper;

	@Override
	public void syncChargedata(List<ChargeWallet> chargeVOlist) {
		for(ChargeWallet temp:chargeVOlist) {
			super.save(temp);
		}
	}
	
    @Override
    public ChargeWallet createChargeWallet(ChargeWallet chargeWallet) {
		// 账户号生成
    	chargeWallet.setAccountNo(super.snowFlake.nextId());
    	chargeWallet.setStatus(TouCengConstant.ONE);
		super.save(chargeWallet);
		log.info("【手续费账户Service】-success-创建手续费账户成功：{}",JSON.toJSONString(chargeWallet));
		return chargeWallet;
    }

    @Override
    public ChargeWallet getChargeWallet(String currency, String channelAccountNo) {
		ChargeWallet chargeWallet = super.getAccountByExample("channelAccountNo",channelAccountNo,currency,ChargeWallet.class);
		if (null == chargeWallet) {
			 chargeWallet = new ChargeWallet();
			 chargeWallet.setChannelAccountNo(channelAccountNo);
			 chargeWallet.setCurrency(currency);
			 log.info("【手续费账户Service】-info-手续费账户为空，准备创建手续费账户,参数：{}",JSON.toJSONString(chargeWallet));
			// 手续费钱包账户为空，创建手续费钱包账户
			chargeWallet = this.createChargeWallet(chargeWallet);
		}

		// 账户校验
		if (TouCengConstant.ONE != chargeWallet.getStatus().intValue()) {
			log.info("【手续费账户Service】-error-手续费账户状态错误：{}",JSON.toJSONString(chargeWallet));
			throw new BusinessException("【手续费账户Service】手续费账户处于非正常状态");
		}
		log.info("【手续费账户Service】-info-获取手续费账户信息：{}",JSON.toJSONString(chargeWallet));
		return chargeWallet;
    }

    @Override
    public boolean updateChargeWallet(String channelAccountNo, String currency, BigDecimal amount,BigDecimal bizType) {
    	amount = amount.multiply(bizType);
 		boolean result = chargeWalletMapper.updateWallet(channelAccountNo, currency, amount) == TouCengConstant.ONE;
		log.info("【手续费账户Service】-info-修改手续费账户,参数,channelAccountNo:{},currency:{},amount:{},结果:{}",channelAccountNo,currency,amount,result);
		if(!result) {
			throw new BusinessException("【手续费账户Service】，手续费账户可用余额不足");
		}
		return result;
    }

    /** 
    * @Description: 账户手续费查询
    * @Param: [listPageDTO] 
    * @return: com.touceng.common.base.PageInfo<com.touceng.domain.entity.finance.ChargeWallet> 
    * @Author: yangxu 
    * @Date: 2018/9/12 
    */
	@Override
	public PageInfo<ChargeWallet> pageChargeWallet(ListPageDTO listPageDTO) {
		return new PageInfo<ChargeWallet>(chargeWalletMapper.pageChargeWallet(listPageDTO));
	}


}
