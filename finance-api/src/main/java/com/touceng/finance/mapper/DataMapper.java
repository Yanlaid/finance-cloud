package com.touceng.finance.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.touceng.common.constant.TouCengConstant;
import com.touceng.domain.entity.finance.ChannelWallet;
import com.touceng.domain.entity.finance.UserWallet;
import com.touceng.domain.sync.dto.SyncOrderDTO;
import com.touceng.domain.sync.dto.SyncProfitShareDTO;

/**
 * <p>
 * 代理商钱包账户 Mapper 接口
 * </p>
 *
 * @author Wu,Hua-Zheng
 * @since 2018-08-27
 */
@Repository
public interface DataMapper {

	/**
	 * @methodDesc: 方法描述: 获取代理/用户数据
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月3日 下午10:01:54
	 * @version v1.0.0
	 */
	@Select(TouCengConstant.TC_SQL_USER_AGENT)
	public List<UserWallet> getUserAgentData();

	/**
	 * @methodDesc: 方法描述: 获取内部渠道账户
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月3日 下午10:02:50
	 * @version v1.0.0
	 */
	@Select(TouCengConstant.TC_SQL_CHANNEL)
	public List<ChannelWallet> getChannelData();

	/**
	 * @methodDesc: 方法描述: 获取收款数据
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月3日 下午10:03:05
	 * @version v1.0.0
	 */
	@Select(TouCengConstant.TC_SQL_TABLE_ORDER_PAY)
	public List<SyncOrderDTO> getPayOrderData();

	/**
	 * @methodDesc: 方法描述: 获取付款数据
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月3日 下午10:03:20
	 * @version v1.0.0
	 */
	@Select(TouCengConstant.TC_SQL_ORDER_WITHDRAW)
	public List<SyncOrderDTO> getWithdrawOrderData();

	/**
	 * @methodDesc: 方法描述: 获取分润账户数据
	 * @author Wu,Hua-Zheng
	 * @createTime 2018年9月3日 下午10:03:20
	 * @version v1.0.0
	 */
	@Select(TouCengConstant.TC_SQL_PROFIT_SHARE)
	public List<SyncProfitShareDTO> getProfitShareData(@Param("param")  String param);

}