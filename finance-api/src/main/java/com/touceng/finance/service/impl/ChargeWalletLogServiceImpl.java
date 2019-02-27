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
import com.touceng.domain.entity.finance.ChargeWalletLog;
import com.touceng.finance.mapper.ChargeWalletLogMapper;
import com.touceng.finance.service.IChargeWalletLogService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 手续费钱包账户记录日志 服务实现类
 * </p>
 *
 * @author Wu,Hua-Zheng
 * @since 2018-08-27
 */
@Slf4j
@Service
@Transactional
public class ChargeWalletLogServiceImpl extends BaseServiceImpl<ChargeWalletLog> implements IChargeWalletLogService {
	
	@Autowired
    private ChargeWalletLogMapper chargeWalletLogMapper;

    @Override
    public ChargeWalletLog saveChargeWalletLog(ChargeWalletLog chargeWalletLog) {
    	super.save(chargeWalletLog);
        log.info("【手续费账户流水Service】-success-保存手续费账户流成功,订单流水:{}",JSON.toJSONString(chargeWalletLog));
		return chargeWalletLog;
    }

    @Override
    public int updateChargeWalletLog(String orderNo, String currency, int status) {
        return chargeWalletLogMapper.updatelWalletLog(orderNo, currency, status);
    }

	@Override
	public BigDecimal getAmountByParams(String orderNo, String currency, int status) {
		 
		return chargeWalletLogMapper.getAmountByParams(orderNo, currency, status);
	}
	
	@Override
	public List<String> getChannelAccountNo(String orderNo, String currency, int status) {
	 
		return new ArrayList<>(chargeWalletLogMapper.getChannelAccountNo(orderNo, currency, status));
	}

	/** 
	* @Description: 账户手续费流水
	* @Param: [listPageDTO] 
	* @return: com.touceng.common.base.PageInfo<com.touceng.domain.entity.finance.ChargeWalletLog> 
	* @Author: yangxu 
	* @Date: 2018/9/12 
	*/
	@Override
	public PageInfo<ChargeWalletLog> pageChargeWallet(ListPageDTO listPageDTO) {
		return new PageInfo<ChargeWalletLog>(chargeWalletLogMapper.pageChargeWalletlog(listPageDTO));
	}
}
