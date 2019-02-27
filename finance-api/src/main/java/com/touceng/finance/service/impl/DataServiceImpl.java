package com.touceng.finance.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.touceng.common.constant.TCConfigConstant;
import com.touceng.common.constant.TouCengConstant;
import com.touceng.common.utils.HttpClientUtils;
import com.touceng.common.utils.SnowFlakeUtils;
import com.touceng.domain.constant.TouCengDataConstant;
import com.touceng.domain.entity.finance.ChannelWallet;
import com.touceng.domain.entity.finance.ChannelWalletLog;
import com.touceng.domain.entity.finance.ChargeWallet;
import com.touceng.domain.entity.finance.ChargeWalletLog;
import com.touceng.domain.entity.finance.CostWallet;
import com.touceng.domain.entity.finance.CostWalletLog;
import com.touceng.domain.entity.finance.UserWallet;
import com.touceng.domain.entity.finance.UserWalletLog;
import com.touceng.domain.sync.dto.SyncOrderDTO;
import com.touceng.domain.sync.dto.SyncProfitShareDTO;
import com.touceng.finance.mapper.DataMapper;
import com.touceng.finance.service.DataService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 代理商钱包账户 服务实现类
 * </p>
 *
 * @author Wu, Hua-Zheng
 * @since 2018-08-27
 */
@Slf4j
@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DataMapper dataMapper;
    @Autowired
    public SnowFlakeUtils snowFlake;

    @Override
    public String syncData() {

         // TouCengDataConstant.agentMap.clear();
        TouCengDataConstant.channelMap.clear();
        TouCengDataConstant.costMap.clear();
        TouCengDataConstant.chargeMap.clear();

        TouCengDataConstant.userLogList = new LinkedList<>();
        //TouCengDataConstant.agentLogList = new LinkedList<>();
        TouCengDataConstant.channelLogList = new LinkedList<>();
        TouCengDataConstant.costLogList = new LinkedList<>();
        TouCengDataConstant.chargeLogList = new LinkedList<>();


        CostWallet costWallet = null;
        ChargeWallet chargeWallet = null;

        UserWalletLog userWalletLog = null;
        //AgentWalletLog agentWalletLog = null;
        ChannelWalletLog channelWalletLog = null;
        CostWalletLog costWalletLog = null;
        ChargeWalletLog chargeWalletLog = null;

        UserWalletLog userWalletLogTarget = null;
        ChannelWalletLog channelWalletLogTarget = null;
        ChargeWalletLog chargeWalletLogTarget = null;


        List<UserWallet> userAgentList = dataMapper.getUserAgentData();

        if (!CollectionUtils.isEmpty(userAgentList)) {
            for (UserWallet userWallet : userAgentList) {
                userWallet.setAccountNo(snowFlake.nextId());
                TouCengDataConstant.userMap.put(userWallet.getUserId(), userWallet);
            }
        }

        Map<String, String> channelIdMap = new HashMap<>();
        List<ChannelWallet> channelList = dataMapper.getChannelData();
        if (!CollectionUtils.isEmpty(channelList)) {

            for (ChannelWallet channelWallet : channelList) {

                channelIdMap.put(channelWallet.getChannelAccountId(), channelWallet.getChannelAccountNo());

                channelWallet.setAccountNo(snowFlake.nextId());
                TouCengDataConstant.channelMap.put(channelWallet.getChannelAccountNo(), channelWallet);

                costWallet = new CostWallet();
                costWallet.setAccountNo(snowFlake.nextId());
                costWallet.setChannelAccountNo(channelWallet.getChannelAccountNo());
                TouCengDataConstant.costMap.put(channelWallet.getChannelAccountNo(), costWallet);

                chargeWallet = new ChargeWallet();
                chargeWallet.setAccountNo(snowFlake.nextId());
                chargeWallet.setChannelAccountNo(channelWallet.getChannelAccountNo());
                TouCengDataConstant.chargeMap.put(channelWallet.getChannelAccountNo(), chargeWallet);

            }
        }

        UserWallet userWallet = null;
        UserWallet aWallet = null;
        ChannelWallet channelWallet = null;
        BigDecimal floorAgentProfitAmount = null;// 代理分润
        BigDecimal floorCostProfitAmount = null;// 成本手续费
        BigDecimal retailProfitAmount = null;// 零售手续费
        BigDecimal agentShareAmount = BigDecimal.ZERO;// 代理账户支出金额
        List<SyncProfitShareDTO> profitShareVOList = null;
        // 收款-内扣
        List<SyncOrderDTO> payOrderList = dataMapper.getPayOrderData();
        if (!CollectionUtils.isEmpty(payOrderList)) {
            for (SyncOrderDTO orderVO : payOrderList) {

                if (StringUtils.isBlank(orderVO.getChannelAccountId()) && orderVO.getChargeFee().compareTo(BigDecimal.ZERO) == 0) {
                    // log.info(JSON.toJSONString(orderVO));
                    continue;
                }

//                if (!"800055100220002".equals(channelIdMap.get(orderVO.getChannelAccountId()))) {
//                    continue;
//                }


                // PayPipe 费用类型 0费率 1 固定额
                if (orderVO.getCostFeeType() == TouCengConstant.ZERO) {
                    // 成本手续费=订单金额*渠道成本
                    floorCostProfitAmount = orderVO.getOrderAmount().multiply(orderVO.getCostFee()).multiply(TouCengConstant.TC_UNIT_THOUSAND);
                    // 分润=订单金额*（零售费率-代理底价）
                    floorAgentProfitAmount = orderVO.getOrderAmount().multiply(orderVO.getChargeFee().subtract(orderVO.getAgentRebate())).multiply(TouCengConstant.TC_UNIT_THOUSAND);
                    // 零售手续费=订单金额*零售费率
                    retailProfitAmount = orderVO.getOrderAmount().multiply(orderVO.getChargeFee()).multiply(TouCengConstant.TC_UNIT_THOUSAND);
                } else {
                    // 成本手续费=订单金额*渠道成本
                    floorCostProfitAmount = orderVO.getCostFee().multiply(TouCengConstant.TC_UNIT_THOUSAND);
                    // 分润=订单金额*（零售费率-代理底价）
                    floorAgentProfitAmount = TouCengConstant.TC_UNIT_THOUSAND.multiply(orderVO.getChargeFee().subtract(orderVO.getAgentRebate()));
                    // 零售手续费=订单金额*零售费率
                    retailProfitAmount = orderVO.getChargeFee().multiply(TouCengConstant.TC_UNIT_THOUSAND);
                }

                // 用户收入 = 订单金额-零售手续费
                userWallet = TouCengDataConstant.userMap.get(orderVO.getUserId());
                userWalletLog = new UserWalletLog();
                BeanUtils.copyProperties(orderVO, userWalletLog);

                //收入  订单金额
                userWalletLog.setUserId(userWallet.getUserId());
                userWalletLog.setUserCode(userWallet.getUserCode());
                userWalletLog.setUserType(userWallet.getUserType());
                userWalletLog.setUserWalletNo(userWallet.getAccountNo());
                userWalletLog.setIncome(orderVO.getOrderAmount());
                userWalletLog.setBalance(userWallet.getBalance().add(orderVO.getOrderAmount()));
                TouCengDataConstant.userLogList.add(userWalletLog);
                userWalletLogTarget = new UserWalletLog();
                BeanUtils.copyProperties(userWalletLog, userWalletLogTarget);
                //支出 零售手续费
                userWalletLogTarget.setProductCode(TouCengConstant.RETAIL_PROFIT_CODE);
                userWalletLogTarget.setProductName(TouCengConstant.RETAIL_PROFIT_NAME);
                userWalletLogTarget.setIncome(BigDecimal.ZERO);
                userWalletLogTarget.setOutcome(retailProfitAmount);
                userWalletLogTarget.setBalance(userWalletLog.getBalance().subtract(retailProfitAmount));
                TouCengDataConstant.userLogList.add(userWalletLogTarget);
                userWallet.setBalance(userWalletLogTarget.getBalance());

                // 内部渠道账户 = 订单金额-成本手续费
                channelWallet = TouCengDataConstant.channelMap.get(channelIdMap.get(orderVO.getChannelAccountId()));

                //收入 订单
                channelWalletLog = new ChannelWalletLog();
                BeanUtils.copyProperties(orderVO, channelWalletLog);
                channelWalletLog.setChannelAccountNo(channelWallet.getChannelAccountNo());
                channelWalletLog.setIncome(orderVO.getOrderAmount());
                channelWalletLog.setBalance(channelWallet.getBalance().add(orderVO.getOrderAmount()));
                TouCengDataConstant.channelLogList.add(channelWalletLog);

                channelWalletLogTarget = new ChannelWalletLog();
                BeanUtils.copyProperties(channelWalletLog, channelWalletLogTarget);

                //支出 成本手续费
                channelWalletLogTarget.setProductCode(TouCengConstant.COST_PROFIT_CODE);
                channelWalletLogTarget.setProductName(TouCengConstant.COST_PROFIT_NAME);
                channelWalletLogTarget.setIncome(BigDecimal.ZERO);
                channelWalletLogTarget.setOutcome(retailProfitAmount);
                channelWalletLogTarget.setBalance(channelWalletLog.getBalance().subtract(floorCostProfitAmount));
                TouCengDataConstant.channelLogList.add(channelWalletLogTarget);
                channelWallet.setBalance(channelWalletLogTarget.getBalance());

                // 内部手续费账户 = 零售手续费-分润
                chargeWallet = TouCengDataConstant.chargeMap.get(channelWallet.getChannelAccountNo());
                chargeWalletLog = new ChargeWalletLog();
                BeanUtils.copyProperties(orderVO, chargeWalletLog);
                chargeWalletLog.setChannelAccountNo(chargeWallet.getChannelAccountNo());
                chargeWalletLog.setChargeWalletNo(chargeWallet.getAccountNo());
                chargeWalletLog.setProductCode(TouCengConstant.RETAIL_PROFIT_CODE);
                chargeWalletLog.setProductName(TouCengConstant.RETAIL_PROFIT_NAME);
                chargeWalletLog.setIncome(retailProfitAmount);
                chargeWalletLog.setBalance(chargeWallet.getBalance().add(retailProfitAmount));
                TouCengDataConstant.chargeLogList.add(chargeWalletLog);


                chargeWalletLogTarget = new ChargeWalletLog();
                BeanUtils.copyProperties(chargeWalletLog, chargeWalletLogTarget);
                //支出 分润
                chargeWalletLogTarget.setProductCode(TouCengConstant.AGENT_PROFIT_CODE);
                chargeWalletLogTarget.setProductName(TouCengConstant.AGENT_PROFIT_NAME);
                chargeWalletLogTarget.setIncome(BigDecimal.ZERO);
                chargeWalletLogTarget.setOutcome(floorAgentProfitAmount);
                chargeWalletLogTarget.setBalance(chargeWalletLog.getBalance().subtract(floorAgentProfitAmount));
                TouCengDataConstant.chargeLogList.add(chargeWalletLogTarget);
                chargeWallet.setBalance(chargeWalletLogTarget.getBalance());

                // 内部成本账户
                costWallet = TouCengDataConstant.costMap.get(channelWallet.getChannelAccountNo());
                costWalletLog = new CostWalletLog();
                BeanUtils.copyProperties(orderVO, costWalletLog);
                costWalletLog.setCostWalletNo(costWallet.getAccountNo());
                costWalletLog.setChannelAccountNo(costWallet.getChannelAccountNo());
                costWalletLog.setIncome(floorCostProfitAmount);
                costWalletLog.setProductCode(TouCengConstant.COST_PROFIT_CODE);
                costWalletLog.setProductName(TouCengConstant.COST_PROFIT_NAME);
                costWalletLog.setBalance(costWallet.getBalance().add(floorCostProfitAmount));
                TouCengDataConstant.costLogList.add(costWalletLog);

                costWallet.setBalance(costWalletLog.getBalance());

                // 查询分润账户
                profitShareVOList = dataMapper.getProfitShareData(orderVO.getUserProductId());
                if (!CollectionUtils.isEmpty(profitShareVOList)) {
                    for (SyncProfitShareDTO profitShareVO : profitShareVOList) {

                        if (orderVO.getCostFeeType() == TouCengConstant.ZERO) {
                            agentShareAmount = orderVO.getOrderAmount().multiply(profitShareVO.percent).multiply(TouCengConstant.TC_UNIT_THOUSAND);
                        } else {
                            agentShareAmount = profitShareVO.percent.multiply(TouCengConstant.TC_UNIT_THOUSAND);
                        }

                        aWallet = TouCengDataConstant.userMap.get(profitShareVO.getUid());
                        if (null != aWallet) {
                            userWalletLog = new UserWalletLog();
                            BeanUtils.copyProperties(orderVO, userWalletLog);
                            userWalletLog.setUserId(aWallet.getUserId());
                            userWalletLog.setUserCode(aWallet.getUserCode());
                            userWalletLog.setUserType(aWallet.getUserType());
                            userWalletLog.setUserWalletNo(aWallet.getAccountNo());
                            userWalletLog.setIncome(agentShareAmount);
                            userWalletLog.setProductCode(TouCengConstant.AGENT_PROFIT_CODE);
                            userWalletLog.setProductName(TouCengConstant.AGENT_PROFIT_NAME);
                            userWalletLog.setBalance(aWallet.getBalance().add(agentShareAmount));
                            TouCengDataConstant.userLogList.add(userWalletLog);
                            aWallet.setBalance(userWalletLog.getBalance());
                            floorAgentProfitAmount = floorAgentProfitAmount.subtract(agentShareAmount);
                        }

                    }
                }

                // 判断当前用户是否有代理
                if (StringUtils.isBlank(userWallet.getAgentId()) || null == TouCengDataConstant.userMap.get(userWallet.getAgentId())) {
                    // 暂无代理
                    continue;
                }
                aWallet = TouCengDataConstant.userMap.get(userWallet.getAgentId());
                userWalletLog = new UserWalletLog();
                BeanUtils.copyProperties(orderVO, userWalletLog);

                userWalletLog.setUserId(aWallet.getUserId());
                userWalletLog.setUserCode(aWallet.getUserCode());
                userWalletLog.setUserType(aWallet.getUserType());
                userWalletLog.setUserWalletNo(aWallet.getAccountNo());
                userWalletLog.setIncome(agentShareAmount);
                userWalletLog.setProductCode(TouCengConstant.AGENT_PROFIT_CODE);
                userWalletLog.setProductName(TouCengConstant.AGENT_PROFIT_NAME);
                userWalletLog.setBalance(aWallet.getBalance().add(floorAgentProfitAmount));
                TouCengDataConstant.userLogList.add(userWalletLog);
                aWallet.setBalance(userWalletLog.getBalance());
            }

        }

        // 付款内扣
        List<SyncOrderDTO> withdrawOrderList = dataMapper.getWithdrawOrderData();
        if (!CollectionUtils.isEmpty(withdrawOrderList)) {
            for (SyncOrderDTO orderVO : withdrawOrderList) {

                if (StringUtils.isBlank(orderVO.getChannelAccountId()) && orderVO.getChargeFee().compareTo(BigDecimal.ZERO) == 0) {
                    //log.info(JSON.toJSONString(orderVO));
                    continue;
                }
//
//                if (!"800055100220002".equals(channelIdMap.get(orderVO.getChannelAccountId()))) {
//                    continue;
//                }
                // PayPipe 费用类型 0费率 1 固定额
                if (orderVO.getCostFeeType() == TouCengConstant.ZERO) {
                    // 成本手续费=订单金额*渠道成本
                    floorCostProfitAmount = orderVO.getOrderAmount().multiply(orderVO.getCostFee()).multiply(TouCengConstant.TC_UNIT_THOUSAND);
                    // 分润=订单金额*（零售费率-代理底价）
                    //floorAgentProfitAmount = orderVO.getOrderAmount().multiply(orderVO.getChargeFee().subtract(orderVO.getAgentRebate())).multiply(TouCengConstant.TC_UNIT_THOUSAND);
                    // 零售手续费=订单金额*零售费率
                    retailProfitAmount = orderVO.getOrderAmount().multiply(orderVO.getChargeFee()).multiply(TouCengConstant.TC_UNIT_THOUSAND);
                } else {
                    // 成本手续费=订单金额*渠道成本
                    floorCostProfitAmount = orderVO.getCostFee().multiply(TouCengConstant.TC_UNIT_THOUSAND);
                    // 分润=订单金额*（零售费率-代理底价）
                    //floorAgentProfitAmount = TouCengConstant.TC_UNIT_THOUSAND.multiply(orderVO.getChargeFee().subtract(orderVO.getAgentRebate()));
                    // 零售手续费=订单金额*零售费率
                    retailProfitAmount = orderVO.getChargeFee().multiply(TouCengConstant.TC_UNIT_THOUSAND);
                }


                // 用户支出 = 订单金额+零售手续费
                userWallet = TouCengDataConstant.userMap.get(orderVO.getUserId());
                userWalletLog = new UserWalletLog();
                BeanUtils.copyProperties(orderVO, userWalletLog);
                userWalletLog.setUserType(userWallet.getUserType());

                //支出
                userWalletLog.setUserId(userWallet.getUserId());
                userWalletLog.setUserCode(userWallet.getUserCode());
                userWalletLog.setUserWalletNo(userWallet.getAccountNo());
                userWalletLog.setOutcome(orderVO.getOrderAmount());
                userWalletLog.setBalance(userWallet.getBalance().subtract(orderVO.getOrderAmount()));
                TouCengDataConstant.userLogList.add(userWalletLog);

                userWalletLogTarget = new UserWalletLog();
                BeanUtils.copyProperties(userWalletLog, userWalletLogTarget);
                //支出 零售手续费
                userWalletLogTarget.setProductCode(TouCengConstant.RETAIL_PROFIT_CODE);
                userWalletLogTarget.setProductName(TouCengConstant.RETAIL_PROFIT_NAME);
                userWalletLogTarget.setOutcome(retailProfitAmount);
                userWalletLogTarget.setBalance(userWalletLog.getBalance().subtract(retailProfitAmount));
                TouCengDataConstant.userLogList.add(userWalletLogTarget);
                userWallet.setBalance(userWalletLogTarget.getBalance());

                // 内部渠道账户 支出 订单金额
                channelWallet = TouCengDataConstant.channelMap.get(channelIdMap.get(orderVO.getChannelAccountId()));
                channelWalletLog = new ChannelWalletLog();
                BeanUtils.copyProperties(orderVO, channelWalletLog);
                channelWalletLog.setChannelAccountNo(channelWallet.getChannelAccountNo());
                channelWalletLog.setOutcome(orderVO.getOrderAmount());
                channelWalletLog.setBalance(channelWallet.getBalance().subtract(orderVO.getOrderAmount()));
                TouCengDataConstant.channelLogList.add(channelWalletLog);
                channelWallet.setBalance(channelWalletLog.getBalance());

                //合肥服玩科技有限公司
                if ("800055100220002".equals(channelWallet.getChannelAccountNo())) {
                    //continue;
                    channelWalletLogTarget = new ChannelWalletLog();
                    BeanUtils.copyProperties(channelWalletLog, channelWalletLogTarget);
                    //支出 成本手续费
                    channelWalletLogTarget.setProductCode(TouCengConstant.COST_PROFIT_CODE);
                    channelWalletLogTarget.setProductName(TouCengConstant.COST_PROFIT_NAME);
                    channelWalletLogTarget.setIncome(BigDecimal.ZERO);
                    channelWalletLogTarget.setOutcome(retailProfitAmount);
                    channelWalletLogTarget.setBalance(channelWalletLog.getBalance().subtract(retailProfitAmount));
                    TouCengDataConstant.channelLogList.add(channelWalletLogTarget);
                    channelWallet.setBalance(channelWalletLogTarget.getBalance());
                }


                // 内部手续费账户 = 零售手续费-分润
                chargeWallet = TouCengDataConstant.chargeMap.get(channelWallet.getChannelAccountNo());
                chargeWalletLog = new ChargeWalletLog();
                BeanUtils.copyProperties(orderVO, chargeWalletLog);
                chargeWalletLog.setChargeWalletNo(chargeWallet.getAccountNo());
                chargeWalletLog.setChannelAccountNo(chargeWallet.getChannelAccountNo());
                chargeWalletLog.setProductCode(TouCengConstant.RETAIL_PROFIT_CODE);
                chargeWalletLog.setProductName(TouCengConstant.RETAIL_PROFIT_NAME);
                chargeWalletLog.setIncome(retailProfitAmount);
                chargeWalletLog.setBalance(chargeWallet.getBalance().add(retailProfitAmount));
                TouCengDataConstant.chargeLogList.add(chargeWalletLog);




//                chargeWalletLogTarget = new ChargeWalletLog();
//                BeanUtils.copyProperties(chargeWalletLog, chargeWalletLogTarget);
//                //支出 分润
//                chargeWalletLogTarget.setProductCode(TouCengConstant.AGENT_PROFIT_CODE);
//                chargeWalletLogTarget.setProductName(TouCengConstant.AGENT_PROFIT_NAME);
//                chargeWalletLogTarget.setIncome(BigDecimal.ZERO);
//                chargeWalletLogTarget.setOutcome(floorAgentProfitAmount);
//                chargeWalletLogTarget.setBalance(chargeWalletLog.getBalance().subtract(floorAgentProfitAmount));
//                TouCengDataConstant.chargeLogList.add(chargeWalletLogTarget);
                // chargeWallet.setBalance(chargeWalletLogTarget.getBalance());
                chargeWallet.setBalance(chargeWalletLog.getBalance());

                // 内部成本账户 = 零售手续费-分润
                costWallet = TouCengDataConstant.costMap.get(channelWallet.getChannelAccountNo());
                costWalletLog = new CostWalletLog();
                BeanUtils.copyProperties(orderVO, costWalletLog);
                costWalletLog.setCostWalletNo(costWallet.getAccountNo());
                costWalletLog.setChannelAccountNo(costWallet.getChannelAccountNo());
                costWalletLog.setIncome(floorCostProfitAmount);
                costWalletLog.setProductCode(TouCengConstant.COST_PROFIT_CODE);
                costWalletLog.setProductName(TouCengConstant.COST_PROFIT_NAME);
                costWalletLog.setBalance(costWallet.getBalance().add(floorCostProfitAmount));
                TouCengDataConstant.costLogList.add(costWalletLog);
                costWallet.setBalance(costWalletLog.getBalance());

                // 查询分润账户
//                profitShareVOList = dataMapper.getProfitShareData(orderVO.getUserProductId());
//                if (!CollectionUtils.isEmpty(profitShareVOList)) {
//                    for (SyncProfitShareVO profitShareVO : profitShareVOList) {
//
//                        if (orderVO.getCostFeeType() == TouCengConstant.ZERO) {
//                            agentShareAmount = orderVO.getOrderAmount().multiply(profitShareVO.percent).multiply(TouCengConstant.TC_UNIT_THOUSAND);
//                        } else {
//                            agentShareAmount = profitShareVO.percent.multiply(TouCengConstant.TC_UNIT_THOUSAND);
//                        }
//
//                        aWallet = userAgentMap.get(profitShareVO.getUid());
//                        if (null != aWallet) {
//                            agentWalletLog = new AgentWalletLog();
//                            BeanUtils.copyProperties(orderVO, agentWalletLog);
//
//                            agentWalletLog.setAgentId(aWallet.getUserId());
//                            agentWalletLog.setAgentWalletNo(aWallet.getAccountNo());
//                            agentWalletLog.setIncome(agentShareAmount);
//                            agentWalletLog.setProductCode(TouCengConstant.AGENT_PROFIT_CODE);
//                            agentWalletLog.setProductName(TouCengConstant.AGENT_PROFIT_NAME);
//                            agentWalletLog.setBalance(aWallet.getBalance().add(agentShareAmount));
//                            TouCengDataConstant.agentLogList.add(agentWalletLog);
//                            aWallet.setBalance(agentWalletLog.getBalance());
//                            floorAgentProfitAmount = floorAgentProfitAmount.subtract(agentShareAmount);
//                        }
//
//                    }
//                }

                // 判断当前用户是否有代理
//                if (StringUtils.isBlank(userWallet.getAgentId()) || null == userAgentMap.get(userWallet.getAgentId())) {
//                    // 暂无代理
//                    continue;
//                }

//                aWallet = userAgentMap.get(userWallet.getAgentId());
//                agentWalletLog = new AgentWalletLog();
//                BeanUtils.copyProperties(orderVO, agentWalletLog);
//
//                agentWalletLog.setAgentId(aWallet.getUserId());
//                agentWalletLog.setAgentWalletNo(aWallet.getAccountNo());
//                agentWalletLog.setIncome(floorAgentProfitAmount);
//                agentWalletLog.setProductCode(TouCengConstant.AGENT_PROFIT_CODE);
//                agentWalletLog.setProductName(TouCengConstant.AGENT_PROFIT_NAME);
//                agentWalletLog.setBalance(aWallet.getBalance().add(floorAgentProfitAmount));
//                TouCengDataConstant.agentLogList.add(agentWalletLog);
//                aWallet.setBalance(agentWalletLog.getBalance());

            }
        }

//        AgentWallet agentWallet = null;
//        for (UserWallet temp : userAgentMap.values()) {
//            // 代理商
//            if (temp.getUserType() == TouCengConstant.ONE) {
//                agentWallet = new AgentWallet();
//                BeanUtils.copyProperties(temp, agentWallet);
//                agentWallet.setAgentId(temp.getUserId());
//                agentWallet.setAgentCode(temp.getUserCode());
//                TouCengDataConstant.agentMap.put(temp.getUserId(), agentWallet);
//            } else {
//                TouCengDataConstant.userMap.put(temp.getUserId(), temp);
//            }
//        }

        log.info("数据同步成功，userAgentMap:{},channel:{},charge:{},cost:{}",
                JSON.toJSONString(TouCengDataConstant.userMap),
                JSON.toJSONString(TouCengDataConstant.channelMap), JSON.toJSONString(TouCengDataConstant.costMap),
                JSON.toJSONString(TouCengDataConstant.chargeMap));

        

        new Thread() {
            @Override
            public void run() {
                HttpClientUtils.reqPost(TCConfigConstant.http_finance_prefix + "user/info", TouCengDataConstant.userMap.values(), null);
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                HttpClientUtils.reqPost(TCConfigConstant.http_finance_prefix + "user/log", TouCengDataConstant.userLogList, null);
            }
        }.start();


        new Thread() {
            @Override
            public void run() {
                HttpClientUtils.reqPost(TCConfigConstant.http_finance_prefix + "channel/info", TouCengDataConstant.channelMap.values(), null);
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                HttpClientUtils.reqPost(TCConfigConstant.http_finance_prefix + "channel/log", TouCengDataConstant.channelLogList, null);
            }
        }.start();


        new Thread() {
            @Override
            public void run() {
                HttpClientUtils.reqPost(TCConfigConstant.http_finance_prefix + "cost/info", TouCengDataConstant.costMap.values(), null);
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                HttpClientUtils.reqPost(TCConfigConstant.http_finance_prefix + "cost/log", TouCengDataConstant.costLogList, null);

            }
        }.start();


        new Thread() {
            @Override
            public void run() {
                HttpClientUtils.reqPost(TCConfigConstant.http_finance_prefix + "charge/info", TouCengDataConstant.chargeMap.values(), null);
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                HttpClientUtils.reqPost(TCConfigConstant.http_finance_prefix + "charge/log", TouCengDataConstant.chargeLogList, null);
            }
        }.start();

        return null;
    }

}