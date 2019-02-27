package com.touceng.finance.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.touceng.common.base.BaseServiceImpl;
import com.touceng.common.constant.TouCengConstant;
import com.touceng.common.exception.BusinessException;
import com.touceng.domain.entity.finance.AgentWallet;
import com.touceng.finance.mapper.AgentWalletMapper;
import com.touceng.finance.service.IAgentWalletService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 代理商钱包账户 服务实现类
 * </p>
 *
 * @author Wu,Hua-Zheng
 * @since 2018-08-27
 */
@Deprecated
@Slf4j
@Service
@Transactional
public class AgentWalletServiceImpl extends BaseServiceImpl<AgentWallet> implements IAgentWalletService {

	@Autowired
	private AgentWalletMapper agentWalletMapper;

	@Override
	public void syncAgentdata(List<AgentWallet> agentVOlist) {

		for (AgentWallet temp : agentVOlist) {
			super.save(temp);
		}
	}

	@Override
	public AgentWallet createAgentWallet(AgentWallet agentWallet) {
		// 账户号生成
		agentWallet.setAccountNo(super.snowFlake.nextId());
		agentWallet.setStatus(TouCengConstant.ONE);
		super.save(agentWallet);
		log.info("代理商分润账户：{}", JSON.toJSONString(agentWallet));
		return agentWallet;
	}

	@Override
	public AgentWallet getAgentWallet(String currency, String agentCode, String agentId) {

		AgentWallet agentWallet = super.getAccountByExample("agentId", agentId, currency, AgentWallet.class);
		if (null == agentWallet) {
			agentWallet = new AgentWallet();
			agentWallet.setAgentId(agentId);
			agentWallet.setCurrency(currency);
			agentWallet.setAgentCode(agentCode);
			log.debug("代理商分润账户为空，创建代理商分润账户,参数：{}", JSON.toJSONString(agentWallet));
			// 代理商分润账户为空，创建代理商分润账户
			agentWallet = this.createAgentWallet(agentWallet);
		}

		// 账户校验
		if (TouCengConstant.ONE != agentWallet.getStatus().intValue()) {
			log.debug("代理商分润账户状态错误：{}", JSON.toJSONString(agentWallet));
			throw new BusinessException("建代理商分润账户处于非正常状态");
		}
		return agentWallet;
	}

	@Override
	public boolean updateAgentWallet(String agentId, String currency, BigDecimal amount, BigDecimal bizType) {

		amount = amount.multiply(bizType);
		boolean result = agentWalletMapper.updateWallet(agentId, currency, amount) == TouCengConstant.ONE;
		log.info("修代理商分润账户，参数,agentId:{},currency:{},amount:{},结果:{}", agentId, currency, amount, result);
		if (!result) {
			throw new BusinessException("代理商分润账户金额变动失败");
		}
		return result;
	}

}
