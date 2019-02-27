package com.touceng.finance.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.touceng.common.base.BaseMapper;
import com.touceng.domain.dto.query.ListPageDTO;
import com.touceng.domain.entity.finance.CostWalletLog;

/**
 * <p>
  * 内部成本账户记录日志 Mapper 接口
 * </p>
 *
 * @author Wu,Hua-Zheng
 * @since 2018-08-27
 */
@Repository
public interface CostWalletLogMapper extends BaseMapper<CostWalletLog> {

	@Update("update tc_cost_wallet_log set status = 2, update_time =now() where order_no = #{orderNo} and status =#{status} and currency = #{currency}")
	int updatelWalletLog(@Param("orderNo") String orderNo, @Param("currency") String currency,@Param("status") int status);
	
	
    @Select("select sum(income) from tc_cost_wallet_log where order_no = #{orderNo} and status = #{status} and currency = #{currency}")
	public BigDecimal getAmountByParams(@Param("orderNo") String orderNo,@Param("currency") String currency,@Param("status") int status);
    
    @Select("select DISTINCT channel_account_no from tc_cost_wallet_log where order_no = #{orderNo} and status = #{status} and currency = #{currency}")
	public Set<String> getChannelAccountNo(@Param("orderNo") String orderNo,@Param("currency") String currency,@Param("status") int status);


	List<CostWalletLog> pageCostWalletlog(ListPageDTO listPageDTO);
}