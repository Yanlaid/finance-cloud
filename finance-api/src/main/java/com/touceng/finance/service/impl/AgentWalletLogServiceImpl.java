package com.touceng.finance.service.impl;

import java.util.List;

import com.touceng.common.base.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.touceng.common.base.BaseServiceImpl;
import com.touceng.domain.entity.finance.AgentWalletLog;
import com.touceng.finance.mapper.AgentWalletLogMapper;
import com.touceng.finance.service.IAgentWalletLogService;

/**
 * <p>
 * 代理商钱包账户记录日志 服务实现类
 * </p>
 *
 * @author Wu,Hua-Zheng
 * @since 2018-08-27
 */
@Deprecated
@Service
@Transactional
public class AgentWalletLogServiceImpl extends BaseServiceImpl<AgentWalletLog> implements IAgentWalletLogService {

	@Autowired
	private AgentWalletLogMapper agentWalletLogMapper;

	@Override
	public AgentWalletLog saveAgentWalletLog(AgentWalletLog log) {
		super.save(log);
		return log;
	}

	@Override
	public int updateAgentWallet(String orderNo, String currency, int status) {
		return agentWalletLogMapper.updatelWalletLog(orderNo, currency, status);
	}

	@Override
	public List<AgentWalletLog> getAgentLogByParams(String orderNo, String currency, int status) {
		return agentWalletLogMapper.getAgentLogByParams(orderNo, currency, status);
	}

	@Override
	public PageInfo<AgentWalletLog> queryAgentWalletLog(AgentWalletLog agentWalletLog) {

		return new PageInfo<AgentWalletLog>(agentWalletLogMapper.queryAgentWalletLog(agentWalletLog));
	}
}
