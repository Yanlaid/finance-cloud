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
import com.touceng.domain.entity.finance.ChargeWalletLog;

/**
 * <p>
  * 手续费钱包账户记录日志 Mapper 接口
 * </p>
 *
 * @author Wu,Hua-Zheng
 * @since 2018-08-27
 */
@Repository
public interface ChargeWalletLogMapper extends BaseMapper<ChargeWalletLog> {

	@Update("update tc_charge_wallet_log set status =2, update_time =now() where order_no = #{orderNo} and status =#{status} and currency = #{currency}")
	int updatelWalletLog(@Param("orderNo") String orderNo,@Param("currency") String currency, @Param("status") int status);
	
	
    @Select("select income-outcome from tc_charge_wallet_log where order_no = #{orderNo} and status = #{status} and currency = #{currency}")
	public BigDecimal getAmountByParams(@Param("orderNo")String orderNo,@Param("currency") String currency,@Param("status") int status);
    
    @Select("select DISTINCT channel_account_no from tc_charge_wallet_log where order_no = #{orderNo} and status = #{status} and currency = #{currency}")
	public Set<String> getChannelAccountNo(@Param("orderNo")String orderNo, @Param("currency") String currency,@Param("status") int status);

	List<ChargeWalletLog> pageChargeWalletlog(ListPageDTO listPageDTO);

}