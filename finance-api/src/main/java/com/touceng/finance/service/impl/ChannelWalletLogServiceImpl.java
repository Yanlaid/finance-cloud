package com.touceng.finance.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.touceng.common.base.BaseServiceImpl;
import com.touceng.common.base.PageInfo;
import com.touceng.domain.dto.query.ListPageDTO;
import com.touceng.domain.entity.finance.ChannelWalletLog;
import com.touceng.finance.mapper.ChannelWalletLogMapper;
import com.touceng.finance.service.IChannelWalletLogService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 内部渠道钱包账户记录日志 服务实现类
 * </p>
 *
 * @author Wu,Hua-Zheng
 * @since 2018-08-27
 */
@Slf4j
@Service
@Transactional
public class ChannelWalletLogServiceImpl extends BaseServiceImpl<ChannelWalletLog> implements IChannelWalletLogService {

	@Autowired
	private ChannelWalletLogMapper channelWalletLogMapper;

	@Override
	public ChannelWalletLog saveChannelWalletLog(ChannelWalletLog channelWalletLog) {
		super.save(channelWalletLog);
        log.info("【内部渠道流水Service】-success-保存内部渠道流水成功,订单流水:{}",JSON.toJSONString(channelWalletLog));
		return channelWalletLog;
	}

	@Override
	public int updateChannelWalletLog(String orderNo, String currency, int status) {
		return channelWalletLogMapper.updatelWalletLog(orderNo, currency, status);
	}

	@Override
	public BigDecimal getAmountByParams(String orderNo, String currency, int status) {

		return channelWalletLogMapper.getAmountByParams(orderNo, currency, status);
	}
	
	@Override
	public List<String> getChannelAccountNo(String orderNo, String currency, int status) {
	 
		return new ArrayList<>(channelWalletLogMapper.getChannelAccountNo(orderNo, currency, status));
	}

	/** 
	* @Description: 渠道手续费流水查询 
	* @Param: [listPageDTO] 
	* @return: com.touceng.common.base.PageInfo<com.touceng.domain.entity.finance.ChannelWalletLog> 
	* @Author: yangxu 
	* @Date: 2018/9/12 
	*/
	@Override
	public PageInfo<ChannelWalletLog> pageChannelWallet(ListPageDTO listPageDTO) {
		return new PageInfo<ChannelWalletLog>(channelWalletLogMapper.pageChannelWalletlog(listPageDTO));
	}
}
