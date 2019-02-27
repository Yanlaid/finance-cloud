package com.touceng.finance.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.touceng.common.base.BaseMapper;
import com.touceng.domain.entity.finance.AgentWalletLog;

/**
 * <p>
 * 代理商钱包账户记录日志 Mapper 接口
 * </p>
 *
 * @author Wu,Hua-Zheng
 * @since 2018-08-27
 */
@Repository
public interface AgentWalletLogMapper extends BaseMapper<AgentWalletLog> {

	@Update("update tc_agent_wallet_log set status = 2, update_time =now() where order_no = #{orderNo} and status =#{status} and currency = #{currency}")
	int updatelWalletLog(@Param("orderNo") String orderNo, @Param("currency") String currency,@Param("status")  int status);

	@Select("select agent_id as agentId,income from tc_agent_wallet_log where order_no = #{orderNo} and status = #{status} and currency = #{currency}")
	public List<AgentWalletLog> getAgentLogByParams(@Param("orderNo") String orderNo,@Param("currency")  String currency,@Param("status")  int status);

	List<AgentWalletLog> queryAgentWalletLog(AgentWalletLog agentWalletLog);

}