package com.touceng.finance.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.touceng.common.base.BaseServiceImpl;
import com.touceng.common.base.PageInfo;
import com.touceng.domain.dto.query.ListPageDTO;
import com.touceng.domain.entity.finance.CostWalletLog;
import com.touceng.finance.mapper.CostWalletLogMapper;
import com.touceng.finance.service.ICostWalletLogService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 内部成本账户记录日志 服务实现类
 * </p>
 *
 * @author Wu, Hua-Zheng
 * @since 2018-08-27
 */
@Slf4j
@Service
@Transactional
public class CostWalletLogServiceImpl extends BaseServiceImpl<CostWalletLog> implements ICostWalletLogService {

    @Autowired
    private CostWalletLogMapper costWalletLogMapper;

    @Override
    public CostWalletLog saveCostWalletLog(CostWalletLog costWalletLog) {
        super.save(costWalletLog);
        log.info("【成本账户流水Service】-success-保存成本账户流成功,订单流水:{}",JSON.toJSONString(costWalletLog));
        return costWalletLog;
    }

    @Override
    public int updateCostWalletLog(String orderNo, String currency, int status) {
        return costWalletLogMapper.updatelWalletLog(orderNo, currency, status);
    }

    @Override
    public BigDecimal getAmountByParams(String orderNo, String currency, int status) {

        return costWalletLogMapper.getAmountByParams(orderNo, currency, status);
    }

    @Override
    public List<String> getChannelAccountNo(String orderNo, String currency, int status) {

        return new ArrayList<>(costWalletLogMapper.getChannelAccountNo(orderNo, currency, status));
    }

    /** 
    * @Description: 账户成本流水查询 
    * @Param: [listPageDTO] 
    * @return: com.touceng.common.base.PageInfo<com.touceng.domain.entity.finance.CostWalletLog> 
    * @Author: yangxu 
    * @Date: 2018/9/12 
    */
    @Override
    public PageInfo<CostWalletLog> pageCostWallet(ListPageDTO listPageDTO) {
        return new PageInfo<CostWalletLog>(costWalletLogMapper.pageCostWalletlog(listPageDTO));
    }
}
