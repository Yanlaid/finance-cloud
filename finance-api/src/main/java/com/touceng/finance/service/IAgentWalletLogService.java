package com.touceng.finance.service;

import java.util.List;

import com.touceng.common.base.BaseService;
import com.touceng.common.base.PageInfo;
import com.touceng.domain.entity.finance.AgentWalletLog;

/**
 * <p>
 * 代理商钱包账户记录日志 服务类
 * </p>
 *
 * @author Wu,Hua-Zheng
 * @since 2018-08-27
 */
public interface IAgentWalletLogService extends BaseService<AgentWalletLog> {

    AgentWalletLog saveAgentWalletLog(AgentWalletLog agentWalletLog);
    int updateAgentWallet(String orderNo, String currency, int status);
    public List<AgentWalletLog> getAgentLogByParams(String orderNo, String currency, int status);
    PageInfo<AgentWalletLog> queryAgentWalletLog(AgentWalletLog agentWalletLog);
}
