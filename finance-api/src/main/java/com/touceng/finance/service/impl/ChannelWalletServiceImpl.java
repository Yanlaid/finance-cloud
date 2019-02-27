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
import com.touceng.domain.entity.finance.ChannelWallet;
import com.touceng.finance.mapper.ChannelWalletMapper;
import com.touceng.finance.service.IChannelWalletService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 内部渠道钱包账户 服务实现类
 * </p>
 *
 * @author Wu,Hua-Zheng
 * @since 2018-08-27
 */
@Slf4j
@Service
@Transactional
public class ChannelWalletServiceImpl extends BaseServiceImpl<ChannelWallet> implements IChannelWalletService {

	@Autowired
	private ChannelWalletMapper channelWalletMapper;

	@Override
	public void syncChanneldata(List<ChannelWallet> channelVOlist) {

		for (ChannelWallet temp : channelVOlist) {
			super.save(temp);
		}

	}

	@Override
	public ChannelWallet createChannelWallet(ChannelWallet channelWallet) {
		// 账户号生成
		channelWallet.setAccountNo(super.snowFlake.nextId());
		channelWallet.setStatus(TouCengConstant.ONE);
		super.save(channelWallet);
		log.info("【内部渠道账户Service】-success-创建内部渠道账户成功：{}",JSON.toJSONString(channelWallet));
		return channelWallet;
	}

	@Override
	public ChannelWallet getChannelWallet(String currency, String channelAccountNo, String channelAccountName,String channelName) {

		ChannelWallet channelWallet = super.getAccountByExample("channelAccountNo", channelAccountNo, currency,ChannelWallet.class);
		if (null == channelWallet) {
			channelWallet = new ChannelWallet();
			channelWallet.setChannelAccountNo(channelAccountNo);
			channelWallet.setCurrency(currency);
			channelWallet.setChannelAccountName(channelAccountName);
			channelWallet.setChannelName(channelName);
			log.info("【内部渠道账户Service】-info内部渠道账户为空，准备创建内部渠道账户,参数：{}",JSON.toJSONString(channelWallet));
			// 内部渠道账户为空，创建内部渠道账户
			channelWallet = this.createChannelWallet(channelWallet);
		}

		// 账户校验
		if (TouCengConstant.ONE != channelWallet.getStatus().intValue()) {
			log.info("【内部渠道账户Service】-error-内部渠道账户状态错误：{}",JSON.toJSONString(channelWallet));
			throw new BusinessException("【内部渠道账户Service】内部渠道账户处于非正常状态");
		}
		return channelWallet;
	}

	@Override
	public boolean updateChannelWallet(String channelAccountNo, String currency, BigDecimal amount,
			BigDecimal bizType) {
		amount = amount.multiply(bizType);
		boolean result = channelWalletMapper.updateWallet(channelAccountNo, currency, amount) == TouCengConstant.ONE;
		log.info("【内部渠道账户Service】-info-修内部渠道账户，参数,channelAccountNo:{},currency:{},amount:{},结果:{}", channelAccountNo, currency, amount,result);
		if (!result) {
			throw new BusinessException("【内部渠道账户Service】内部渠道账户可用余额不足");
		}
		return result;
	}

	/** 
	* @Description: 渠道手续费查询 
	* @Param: [listPageDTO] 
	* @return: com.touceng.common.base.PageInfo<com.touceng.domain.entity.finance.ChannelWallet> 
	* @Author: yangxu 
	* @Date: 2018/9/12 
	*/
	@Override
	public PageInfo<ChannelWallet> pageChannelWallet(ListPageDTO listPageDTO) {
		return new PageInfo<ChannelWallet>(channelWalletMapper.pageChannelWallet(listPageDTO));
	}

}
