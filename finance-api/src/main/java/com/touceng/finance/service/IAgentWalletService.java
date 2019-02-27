package com.touceng.finance.service;

import java.math.BigDecimal;
import java.util.List;

import com.touceng.common.base.BaseService;
import com.touceng.domain.entity.finance.AgentWallet;

/**
 * <p>
 * 代理商钱包账户 服务类
 * </p>
 *
 * @author Wu, Hua-Zheng
 * @since 2018-08-27
 */
public interface IAgentWalletService extends BaseService<AgentWallet> {

    /**
     * @methodDesc: 方法描述: 同步代理数据
     * @author Wu, Hua-Zheng
     * @createTime 2018年8月27日 下午9:22:47
     * @version v1.0.0
     */
    public void syncAgentdata(List<AgentWallet> agentVOlist);
    AgentWallet createAgentWallet(AgentWallet agentWallet);
    AgentWallet getAgentWallet(String currency, String agentCode, String agentId);
    boolean updateAgentWallet(String agentId, String currency, BigDecimal amount,BigDecimal bizType);
}
