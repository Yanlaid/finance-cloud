package com.touceng.finance.service.impl;

import java.math.BigDecimal;

import com.touceng.domain.vo.WalletLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.touceng.common.base.BaseServiceImpl;
import com.touceng.common.base.PageInfo;
import com.touceng.domain.dto.query.ListPageDTO;
import com.touceng.domain.entity.finance.UserWalletLog;
import com.touceng.finance.mapper.UserWalletLogMapper;
import com.touceng.finance.service.IUserWalletLogService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 会员钱包账户记录日志 服务实现类
 * </p>
 *
 * @author Wu,Hua-Zheng
 * @since 2018-08-27
 */
@Slf4j
@Service
@Transactional
public class UserWalletLogServiceImpl extends BaseServiceImpl<UserWalletLog> implements IUserWalletLogService {

	@Autowired
	private UserWalletLogMapper userWalletLogMapper;

	@Override
	public UserWalletLog saveUserWalletLog(UserWalletLog userWalletLog) {
		super.save(userWalletLog);
        log.info("【商户账户流水Service】-success-保存商户账户流水成功,订单流水:{}",JSON.toJSONString(userWalletLog));
		return userWalletLog;
	}

	@Override
	public int updateUserWalletLog(String orderNo, String currency, int status) {

		return userWalletLogMapper.updateUserWalletLog(orderNo, currency, status);
	}

	@Override
	public BigDecimal getAmountByParams(String orderNo, String currency, int status) {

		return userWalletLogMapper.getAmountByParams(orderNo, currency, status);
	}

	/** 
	* @Description: 商户账户日志查询 
	* @Param: [listPageDTO] 
	* @return: com.touceng.common.base.PageInfo<com.touceng.domain.vo.WalletLogVO> 
	* @Author: yangxu 
	* @Date: 2018/9/12 
	*/
	@Override
	public PageInfo<WalletLogVO> pageUserWalletLog(ListPageDTO listPageDTO) {

		return new PageInfo<WalletLogVO>(userWalletLogMapper.pageUserWalletLog(listPageDTO));
	}

	/** 
	* @Description: 子商户账户日志查询
	* @Param: [listPageDTO] 
	* @return: com.touceng.common.base.PageInfo<com.touceng.domain.vo.WalletLogVO> 
	* @Author: yangxu 
	* @Date: 2018/9/12 
	*/
	@Override
	public PageInfo<WalletLogVO> pageSubUserWalletLog(ListPageDTO listPageDTO) {
		return new PageInfo<WalletLogVO>(userWalletLogMapper.pageSubUserWalletLog(listPageDTO));
	}

}
