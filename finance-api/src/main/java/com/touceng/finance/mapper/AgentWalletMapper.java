package com.touceng.finance.mapper;

import com.touceng.domain.entity.finance.AgentWallet;
import com.touceng.common.base.BaseMapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * <p>
  * 代理商钱包账户 Mapper 接口
 * </p>
 *
 * @author Wu,Hua-Zheng
 * @since 2018-08-27
 */
@Repository
public interface AgentWalletMapper extends BaseMapper<AgentWallet> {


	@Update("update tc_agent_wallet set balance =balance+ #{amount} , update_time =now() where agent_id = #{agentId} and currency = #{currency}")
	int updateWallet(String agentId, String currency, BigDecimal amount);
	
}