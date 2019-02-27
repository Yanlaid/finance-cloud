package com.touceng.finance.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.touceng.common.constant.TouCengConstant;
import com.touceng.common.exception.BusinessException;
import com.touceng.common.response.EResultEnum;
import com.touceng.common.utils.RedisCacheUtils;
import com.touceng.common.utils.lock.IDistributedLock;
import com.touceng.domain.dto.order.AgentShareDTO;
import com.touceng.domain.dto.order.OrderCreateDTO;
import com.touceng.domain.dto.order.OrderNotifyDTO;
import com.touceng.domain.dto.order.OrderReqDTO;
import com.touceng.domain.dto.order.OrderResDTO;
import com.touceng.domain.entity.finance.ChannelWallet;
import com.touceng.domain.entity.finance.ChannelWalletLog;
import com.touceng.domain.entity.finance.ChargeWallet;
import com.touceng.domain.entity.finance.ChargeWalletLog;
import com.touceng.domain.entity.finance.CostWallet;
import com.touceng.domain.entity.finance.CostWalletLog;
import com.touceng.domain.entity.finance.UserWallet;
import com.touceng.domain.entity.finance.UserWalletLog;
import com.touceng.finance.service.IChannelWalletLogService;
import com.touceng.finance.service.IChannelWalletService;
import com.touceng.finance.service.IChargeWalletLogService;
import com.touceng.finance.service.IChargeWalletService;
import com.touceng.finance.service.ICostWalletLogService;
import com.touceng.finance.service.ICostWalletService;
import com.touceng.finance.service.IUserWalletLogService;
import com.touceng.finance.service.IUserWalletService;
import com.touceng.finance.service.IWalletService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 类描述: 付款收款订单账户变化
 * @createTime 2018年8月29日 下午3:31:17
 * @copyright: 上海投嶒网络技术有限公司
 */
@Slf4j
@Service
@Transactional
public class WalletServiceImpl implements IWalletService {

    @Autowired
    private IUserWalletService userWalletService;
    @Autowired
    private IUserWalletLogService userWalletLogService;

    @Autowired
    private IChannelWalletService channelWalletService;
    @Autowired
    private IChannelWalletLogService channelWalletLogService;

    @Autowired
    private IChargeWalletService chargeWalletService;
    @Autowired
    private IChargeWalletLogService chargeWalletLogService;

    @Autowired
    private ICostWalletService costWalletService;
    @Autowired
    private ICostWalletLogService costWalletLogService;

    @Autowired
    private RedisCacheUtils redisCacheUtils;
    
	@Autowired
	private IDistributedLock distributedLock;


    @Override
    public String withdrawCreate(OrderCreateDTO dto) {

        // 订单列表判断
        if (null == dto || CollectionUtils.isEmpty(dto.getOrderList())) {
            log.info("【付款订单Service】-error-付款参数为空或订单列表为空");
            throw new BusinessException(EResultEnum.BAD_REQUEST.getCode(), "【付款订单Service】参数为空或订单列表为空");
        }

        if (dto.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            log.info("【付款订单Service】-error-订单金额不正确,订单信息:{}", JSON.toJSONString(dto));
            throw new BusinessException(EResultEnum.BAD_REQUEST.getCode(), "【付款订单Service】订单金额不正确，订单信息:"+JSON.toJSONString(dto));
        }


        try {
        	
	        	//获取锁失败
	        	if(!distributedLock.lock(TouCengConstant.ORDER_LOCK_REDIS_KEY)) {
	        		 log.info("【付款订单Service】-error-获取锁失败,订单信息:{}", JSON.toJSONString(dto));
	        		 throw new BusinessException(EResultEnum.REQUEST_REMOTE_ERROR.getCode(), "【付款订单Service】获取锁失败，订单信息:"+JSON.toJSONString(dto));
	        	}

                // 查询用户账户/并创建
                UserWallet userWallet = userWalletService.getUserWallet(dto.getUserId(), dto.getCurrency(),dto.getUserType(), dto.getUserCode(), dto.getAgentId());

                // 商户校验
                if (null == userWallet || (StringUtils.isNoneBlank(dto.getAgentId()) && !dto.getAgentId().equals(userWallet.getAgentId()))) {
                    log.info("【付款订单Service】-error-获取用户信息失败或用户代理商信息不匹配:{}", JSON.toJSONString(dto));
                    throw new BusinessException(EResultEnum.VALIDATE_ERROR.getCode(), "【付款订单Service】获取用户信息失败或用户代理商信息不匹配，订单信息:"+JSON.toJSONString(dto));
                }

                log.info("【付款订单Service】-info-未处理订单前商户信息，商户号码:{}，商户余额:{}", userWallet.getUserCode(),userWallet.getBalance());

                // 付款时，判断支付金额和用户可用余额对比
                if (userWallet.getBalance().compareTo(dto.getTotalAmount()) < 0) {
                    log.info("【付款订单Service】-error-付款金额大于用户可用余额错误，订单总金额:{},用户可用余额:{}", dto.getTotalAmount(), userWallet.getBalance());
                    throw new BusinessException(EResultEnum.VALIDATE_ERROR.getCode(), "【付款订单Service】付款金额大于用户可用余额错误，订单信息:"+JSON.toJSONString(dto)+",用户可用余额:"+ userWallet.getBalance());
                }

                //手续费配置 费用类型-feeType【0-费率；1-固定额】
                int feeType = 0;

                BigDecimal floorCostProfitAmount = null;//成本手续费
                BigDecimal retailProfitAmount = null;//零售手续费
                BigDecimal tempAmount = null;//用户账户支出金额
                UserWalletLog userWalletLog = null;//用户账户变动明细
                UserWalletLog userWalletLogTarget = null;
                ChannelWalletLog channelWalletLogTarget = null;

                for (OrderReqDTO orderReqDTO : dto.getOrderList()) {

                    log.info("【付款订单Service】-info- 付款订单信息:{}",JSON.toJSONString(orderReqDTO));

                    if (orderReqDTO.getOrderAmount().compareTo(BigDecimal.ZERO) <= 0) {
                        log.info("【付款订单Service】-error-订单金额不正确,订单信息:{}", JSON.toJSONString(orderReqDTO));
                        throw new BusinessException(EResultEnum.BAD_REQUEST.getCode(), "【付款订单Service】订单金额不正确,订单信息:" + JSON.toJSONString(orderReqDTO));
                    }

                    if (redisCacheUtils.hasKey(orderReqDTO.getOrderNo())) {
                        log.info("【付款订单Service】-error-订单号码已存在,订单信息:{}", JSON.toJSONString(orderReqDTO));
                        throw new BusinessException(EResultEnum.BAD_REQUEST.getCode(), "【付款订单Service】订单号码已存在,订单信息:" + JSON.toJSONString(orderReqDTO));
                    }
                    
                    feeType = orderReqDTO.getProfitRate().getFeeType();
                    log.info("【付款订单Service】-info-费用原始分成比列，扣款类型【内扣-0/外扣-1】:{},费用类型【0-费率；1-固定额】:{},代理底价:{},成本:{},手续费:{}",orderReqDTO.getWithdrawType(),feeType,orderReqDTO.getProfitRate().getFloorAgentProfit(), orderReqDTO.getProfitRate().getFloorCostProfit(),orderReqDTO.getProfitRate().getRetailProfit());

                    //费用类型-feeType【0-费率；1-固定额】
                    if (TouCengConstant.ZERO == feeType) {

                        //成本手续费=订单金额*渠道成本
                        floorCostProfitAmount = orderReqDTO.getOrderAmount().multiply(BigDecimal.valueOf(orderReqDTO.getProfitRate().getFloorCostProfit())).multiply(TouCengConstant.TC_UNIT_THOUSAND);

                        //零售手续费=订单金额*零售费率
                        retailProfitAmount = orderReqDTO.getOrderAmount().multiply(BigDecimal.valueOf(orderReqDTO.getProfitRate().getRetailProfit())).multiply(TouCengConstant.TC_UNIT_THOUSAND);

                    } else {

                        //成本手续费=订单金额*渠道成本
                        floorCostProfitAmount = BigDecimal.valueOf(orderReqDTO.getProfitRate().getFloorCostProfit()).multiply(TouCengConstant.TC_UNIT_THOUSAND);

                        //零售手续费=订单金额*零售费率
                        retailProfitAmount = BigDecimal.valueOf(orderReqDTO.getProfitRate().getRetailProfit()).multiply(TouCengConstant.TC_UNIT_THOUSAND);
                    }

                    log.info("【付款订单Service】-info-费用分成金额计算结果，费用类型:{},分润:付款暂无分润,成本:{},手续费:{},订单编号:{},订单金额:{}",feeType, floorCostProfitAmount,retailProfitAmount,orderReqDTO.getOrderNo(),orderReqDTO.getOrderAmount());

                    //用户账户金额扣款
                    tempAmount = orderReqDTO.getOrderAmount().add(retailProfitAmount);
                    userWalletService.updateUserWallet(dto.getUserId(), dto.getCurrency(), tempAmount, TouCengConstant.ACCOUNT_TYPE_OUT);
                    log.info("【付款订单Service】-success- 修改【商户账户:{}】余额成功-支出【订单金额:{}+手续费:{}】",userWallet.getUserCode(),orderReqDTO.getOrderAmount(),retailProfitAmount);

                    //用户支出订单金额
                    userWalletLog = new UserWalletLog();
                    //订单信息
                    userWalletLog.setOrderNo(orderReqDTO.getOrderNo());
                    userWalletLog.setOrderAmount(orderReqDTO.getOrderAmount());
                    userWalletLog.setOrderType(dto.getOrderType());
                    userWalletLog.setProductCode(orderReqDTO.getProductCode());
                    userWalletLog.setProductName(orderReqDTO.getProductName());
                    userWalletLog.setCurrency(dto.getCurrency());
                    
                    //用户信息
                    userWalletLog.setUserId(dto.getUserId());
                    userWalletLog.setUserType(dto.getUserType());
                    userWalletLog.setUserCode(userWallet.getUserCode());
                    //用户支出金额
                    userWalletLog.setOutcome(orderReqDTO.getOrderAmount());
                    //用户当前剩余金额
                    userWalletLog.setBalance(userWallet.getBalance().subtract(orderReqDTO.getOrderAmount()));
                    //用户账户号
                    userWalletLog.setUserWalletNo(userWallet.getAccountNo());
                    userWalletLogService.saveUserWalletLog(userWalletLog);
                    log.info("【付款订单Service】-success- 保存【商户账户:{}】流水成功-支出-【订单金额:{}】",userWallet.getUserCode(),orderReqDTO.getOrderAmount());

                    //用户支出零售手续费
                    userWalletLogTarget = new UserWalletLog();
                    BeanUtils.copyProperties(userWalletLog, userWalletLogTarget);
                    userWalletLogTarget.setId(null);
                    userWalletLogTarget.setIncome(BigDecimal.ZERO);
                    userWalletLogTarget.setOutcome(retailProfitAmount);
                    //用户当前剩余金额
                    userWalletLogTarget.setBalance(userWalletLog.getBalance().subtract(retailProfitAmount));
                    //业务类型
                    userWalletLogTarget.setProductCode(TouCengConstant.RETAIL_PROFIT_CODE);
                    //业务类型名称
                    userWalletLogTarget.setProductName(TouCengConstant.RETAIL_PROFIT_NAME);
                    //用户账单手续费保存
                    userWalletLogService.saveUserWalletLog(userWalletLogTarget);
                    log.info("【付款订单Service】-success- 保存【商户账户:{}】流水成功-支出-【手续费:{}】",userWallet.getUserCode(),retailProfitAmount);

                    //渠道商户获取并创建
                    ChannelWallet channelWallet = channelWalletService.getChannelWallet(dto.getCurrency(), orderReqDTO.getChannelAccountNo(), orderReqDTO.getChannelAccountName(), orderReqDTO.getChannelName());
                    log.info("【付款订单Service】-success-获取渠道账户余额,渠道账户号:{}，渠道余额:{}",channelWallet.getChannelAccountNo(),channelWallet.getBalance());


                    //根据扣款类型计算金额  付款【内扣-0/外扣-1】
                    tempAmount = orderReqDTO.getOrderAmount();
                    if (TouCengConstant.ONE == orderReqDTO.getWithdrawType()) {
                        tempAmount = tempAmount.add(floorCostProfitAmount);
                    }
                    channelWalletService.updateChannelWallet(orderReqDTO.getChannelAccountNo(), dto.getCurrency(), tempAmount, TouCengConstant.ACCOUNT_TYPE_OUT);
                    
                    if (TouCengConstant.ONE == orderReqDTO.getWithdrawType()) {
                    	 log.info("【付款订单Service】-success- 修改【内部渠道账户:{}】余额成功-支出外扣【订单金额:{}+成本:{}】",orderReqDTO.getChannelAccountNo(),orderReqDTO.getOrderAmount(),floorCostProfitAmount);
                    }else {
                    	 log.info("【付款订单Service】-success- 修改【内部渠道账户:{}】余额成功-支出内扣【订单金额:{}】",orderReqDTO.getChannelAccountNo(),tempAmount);
                    }
                    
                    //渠道支出订单金额
                    ChannelWalletLog channelWalletLog = new ChannelWalletLog();
                    channelWalletLog.setChannelAccountNo(orderReqDTO.getChannelAccountNo());
                    channelWalletLog.setOrderAmount(orderReqDTO.getOrderAmount());
                    channelWalletLog.setOrderType(dto.getOrderType());
                    //订单号
                    channelWalletLog.setOrderNo(orderReqDTO.getOrderNo());
                    //业务类型
                    channelWalletLog.setProductCode(orderReqDTO.getProductCode());
                    //业务类型名称
                    channelWalletLog.setProductName(orderReqDTO.getProductName());
                    channelWalletLog.setCurrency(dto.getCurrency());
                    //用户支出金额
                    channelWalletLog.setOutcome(orderReqDTO.getOrderAmount());
                    //用户当前剩余金额
                    channelWalletLog.setBalance(channelWallet.getBalance().subtract(orderReqDTO.getOrderAmount()));
                    channelWalletLogService.saveChannelWalletLog(channelWalletLog);
                    log.info("【付款订单Service】-success- 保存【内部渠道账户:{}】流水成功-支出-【订单金额:{}】",orderReqDTO.getChannelAccountNo(),orderReqDTO.getOrderAmount());

                    //付款外扣-渠道支出成本费用
                    if (TouCengConstant.ONE == orderReqDTO.getWithdrawType()) {

                        channelWalletLogTarget = new ChannelWalletLog();
                        BeanUtils.copyProperties(channelWalletLog, channelWalletLogTarget);
                        channelWalletLogTarget.setId(null);
                        channelWalletLogTarget.setIncome(BigDecimal.ZERO);
                        //用户支出金额
                        channelWalletLogTarget.setOutcome(floorCostProfitAmount);
                        //用户当前剩余金额
                        channelWalletLogTarget.setBalance(channelWalletLog.getBalance().subtract(floorCostProfitAmount));
                        //业务类型
                        channelWalletLogTarget.setProductCode(TouCengConstant.COST_PROFIT_CODE);
                        //业务类型名称
                        channelWalletLogTarget.setProductName(TouCengConstant.COST_PROFIT_NAME);
                        channelWalletLogService.saveChannelWalletLog(channelWalletLogTarget);
                        log.info("【付款订单Service】-success- 保存【内部渠道账户:{}】流水成功-支出外扣-【成本:{}】",orderReqDTO.getChannelAccountNo(),floorCostProfitAmount);
                    }


                    //内部手续费
                    ChargeWallet chargeWallet = chargeWalletService.getChargeWallet(dto.getCurrency(), orderReqDTO.getChannelAccountNo());
                    chargeWalletService.updateChargeWallet(orderReqDTO.getChannelAccountNo(), dto.getCurrency(), retailProfitAmount, TouCengConstant.ACCOUNT_TYPE_IN);
                    log.info("【付款订单Service】-success- 修改【手续费账户:{}】余额成功-收入【手续费:{}】",orderReqDTO.getChannelAccountNo(),retailProfitAmount);


                    ChargeWalletLog chargeWalletLog = new ChargeWalletLog();
                    chargeWalletLog.setChannelAccountNo(orderReqDTO.getChannelAccountNo());
                    chargeWalletLog.setOrderAmount(orderReqDTO.getOrderAmount());
                    chargeWalletLog.setOrderType(dto.getOrderType());

                    //订单号
                    chargeWalletLog.setOrderNo(orderReqDTO.getOrderNo());
                    //业务类型
                    chargeWalletLog.setProductCode(TouCengConstant.RETAIL_PROFIT_CODE);
                    //业务类型名称
                    chargeWalletLog.setProductName(TouCengConstant.RETAIL_PROFIT_NAME);
                    chargeWalletLog.setCurrency(dto.getCurrency());
                    chargeWalletLog.setChargeWalletNo(chargeWallet.getAccountNo());
                    //用户支出金额
                    chargeWalletLog.setIncome(retailProfitAmount);
                    //用户当前剩余金额
                    chargeWalletLog.setBalance(chargeWallet.getBalance().add(retailProfitAmount));
                    chargeWalletLogService.saveChargeWalletLog(chargeWalletLog);
                    log.info("【付款订单Service】-success- 保存【手续费账户:{}】流水成功-收入-【手续费:{}】",orderReqDTO.getChannelAccountNo(),retailProfitAmount);


                    //付款外扣有成本
                    if (TouCengConstant.ONE == orderReqDTO.getWithdrawType()) {
                        //成本手续费
                        CostWallet costWallet = costWalletService.getCostWallet(dto.getCurrency(), orderReqDTO.getChannelAccountNo());
                        costWalletService.updateCostWallet(orderReqDTO.getChannelAccountNo(), dto.getCurrency(), floorCostProfitAmount, TouCengConstant.ACCOUNT_TYPE_IN);
                        log.info("【付款订单Service】-success- 修改【成本账户:{}】余额成功-收入【成本:{}】", orderReqDTO.getChannelAccountNo(), floorCostProfitAmount);

                        CostWalletLog costWalletLog = new CostWalletLog();
                        costWalletLog.setChannelAccountNo(orderReqDTO.getChannelAccountNo());
                        costWalletLog.setOrderAmount(orderReqDTO.getOrderAmount());
                        costWalletLog.setOrderType(dto.getOrderType());

                        //订单号
                        costWalletLog.setOrderNo(orderReqDTO.getOrderNo());
                        //业务类型
                        costWalletLog.setProductCode(TouCengConstant.COST_PROFIT_CODE);
                        //业务类型名称
                        costWalletLog.setProductName(TouCengConstant.COST_PROFIT_NAME);
                        costWalletLog.setCurrency(dto.getCurrency());
                        costWalletLog.setCostWalletNo(costWallet.getAccountNo());
                        //用户支出金额
                        costWalletLog.setIncome(floorCostProfitAmount);
                        //用户当前剩余金额
                        costWalletLog.setBalance(costWallet.getBalance().add(floorCostProfitAmount));
                        costWalletLogService.saveCostWalletLog(costWalletLog);
                        log.info("【付款订单Service】-success- 保存【成本账户:{}】流水成功-收入【成本:{}】", orderReqDTO.getChannelAccountNo(), floorCostProfitAmount);
                    }

                    redisCacheUtils.set(orderReqDTO.getOrderNo(), String.valueOf(TouCengConstant.ZERO), TouCengConstant.ORDER_LOCK_REDIS_TIME);
                }
                return String.valueOf(userWalletLogTarget.getBalance());
			
    	} catch (Exception e) {
			log.error("【付款订单Service】-error-[异常信息]:{}",e.getMessage());
			throw new BusinessException("【付款订单Service】-error-[异常信息]:"+e.getMessage());
		}finally {
		    distributedLock.releaseLock(TouCengConstant.ORDER_LOCK_REDIS_KEY);
		}
    }

    @Override
    public String withdrawNotify(OrderNotifyDTO dto) {

        // 订单列表判断
        if (null == dto || CollectionUtils.isEmpty(dto.getOrderList())) {
            log.info("【付款订单回调Service】- error- 付款回调参数为空或订单列表为空");
            throw new BusinessException(EResultEnum.BAD_REQUEST);
        }

        try {
        	
	        	//获取锁失败
	        	if(!distributedLock.lock(TouCengConstant.ORDER_LOCK_REDIS_KEY)) {
	        		 throw new BusinessException(EResultEnum.REQUEST_REMOTE_ERROR.getCode(), "【付款订单回调Service】-error-获取锁失败,订单信息:"+ JSON.toJSONString(dto));
	        	}
        	
                 BigDecimal tempAmount = null;
                 int result = 0;
                 List<String> channelAccountNoList = null;
                 String redisRes = null;
                 for (OrderResDTO orderResDTO : dto.getOrderList()) {

                     if(!dto.getHandle()){
                         redisRes = redisCacheUtils.get(orderResDTO.getOrderNo());
                         if (StringUtils.isBlank(redisRes)) {
                             throw new BusinessException(EResultEnum.BAD_REQUEST.getCode(), "【付款订单回调Service】-error-订单超时，请人工处理,订单信息:" + JSON.toJSONString(orderResDTO));
                         }

                         if (TouCengConstant.ONE == Integer.parseInt(redisRes)) {
                             throw new BusinessException(EResultEnum.BAD_REQUEST.getCode(), "【付款订单回调Service】-error-订单超时，请人工处理,订单信息:" + JSON.toJSONString(orderResDTO));
                         }
                     }

                     //用户金额退款
                     tempAmount = userWalletLogService.getAmountByParams(orderResDTO.getOrderNo(), orderResDTO.getCurrency(), TouCengConstant.ONE);
                     if (null ==  tempAmount ||  tempAmount.compareTo(BigDecimal.ZERO) <= 0) {
                          throw new BusinessException(EResultEnum.BAD_REQUEST.getCode(), "【付款订单回调Service】-error-【商户】订单不存在或回调重复操作,订单号码:" + orderResDTO.getOrderNo());
                     }
                     
                     result = userWalletLogService.updateUserWalletLog(orderResDTO.getOrderNo(), orderResDTO.getCurrency(), TouCengConstant.ONE);
                     if (TouCengConstant.TWO == result) {
                         userWalletService.updateUserWallet(dto.getUserId(), orderResDTO.getCurrency(), tempAmount, TouCengConstant.ACCOUNT_TYPE_IN);
                     }else {
                          throw new BusinessException(EResultEnum.BAD_REQUEST.getCode(), "【付款订单回调Service】-error-修改【商户】余额失败，订单不存在或回调重复操作,订单号码:" + orderResDTO.getOrderNo());
                     }

                     //内部渠道账户
                     tempAmount = channelWalletLogService.getAmountByParams(orderResDTO.getOrderNo(), orderResDTO.getCurrency(), TouCengConstant.ONE);
                     if (null ==  tempAmount || tempAmount.compareTo(BigDecimal.ZERO) <= 0) {
                         throw new BusinessException(EResultEnum.BAD_REQUEST.getCode(), "【付款订单回调Service】-error-【内部渠道账户】订单不存在或回调重复操作,订单号码:" + orderResDTO.getOrderNo());
                    }
                     
                     channelAccountNoList = channelWalletLogService.getChannelAccountNo(orderResDTO.getOrderNo(), orderResDTO.getCurrency(), TouCengConstant.ONE);
                     result = channelWalletLogService.updateChannelWalletLog(orderResDTO.getOrderNo(), orderResDTO.getCurrency(), TouCengConstant.ONE);
                     if ((TouCengConstant.ONE == result || TouCengConstant.TWO == result) && !CollectionUtils.isEmpty(channelAccountNoList) && channelAccountNoList.size() == TouCengConstant.ONE) {
                         channelWalletService.updateChannelWallet(channelAccountNoList.get(TouCengConstant.ZERO), orderResDTO.getCurrency(), tempAmount, TouCengConstant.ACCOUNT_TYPE_IN);
                     }else {
                         throw new BusinessException(EResultEnum.BAD_REQUEST.getCode(), "【付款订单回调Service】-error-修改【内部渠道账户】余额失败，订单不存在或回调重复操作,订单号码:" + orderResDTO.getOrderNo());
                     }

                     //内部手续费
                     tempAmount = chargeWalletLogService.getAmountByParams(orderResDTO.getOrderNo(), orderResDTO.getCurrency(), TouCengConstant.ONE);
                     if (null ==  tempAmount || tempAmount.compareTo(BigDecimal.ZERO) <= 0) {
                         throw new BusinessException(EResultEnum.BAD_REQUEST.getCode(), "【付款订单回调Service】-error-【手续费】订单不存在或回调重复操作,订单号码:" + orderResDTO.getOrderNo());
                    }
                     
                     channelAccountNoList = chargeWalletLogService.getChannelAccountNo(orderResDTO.getOrderNo(), orderResDTO.getCurrency(), TouCengConstant.ONE);
                     result = chargeWalletLogService.updateChargeWalletLog(orderResDTO.getOrderNo(), orderResDTO.getCurrency(), TouCengConstant.ONE);
                     if (TouCengConstant.ONE == result && !CollectionUtils.isEmpty(channelAccountNoList) && channelAccountNoList.size() == TouCengConstant.ONE) {
                         chargeWalletService.updateChargeWallet(channelAccountNoList.get(TouCengConstant.ZERO), orderResDTO.getCurrency(), tempAmount, TouCengConstant.ACCOUNT_TYPE_OUT);
                     }else {
                         throw new BusinessException(EResultEnum.BAD_REQUEST.getCode(), "【付款订单回调Service】-error-修改【手续费】余额失败，订单不存在或回调重复操作,订单号码:" + orderResDTO.getOrderNo());
                     }

                     //付款内扣，没有成本
                     tempAmount = costWalletLogService.getAmountByParams(orderResDTO.getOrderNo(), orderResDTO.getCurrency(), TouCengConstant.ONE);
                     if (null ==  tempAmount || tempAmount.compareTo(BigDecimal.ZERO) == 0) {
                         log.info("【付款订单回调Service】-info-【成本】付款内扣无成本,订单号码:{}",orderResDTO.getOrderNo());
                         //throw new BusinessException(EResultEnum.BAD_REQUEST.getCode(), "【付款订单回调Service】-error-【成本】订单不存在或回调重复操作,订单号码:" + orderResDTO.getOrderNo());
                    }else{
                         channelAccountNoList = costWalletLogService.getChannelAccountNo(orderResDTO.getOrderNo(), orderResDTO.getCurrency(), TouCengConstant.ONE);
                         result = costWalletLogService.updateCostWalletLog(orderResDTO.getOrderNo(), orderResDTO.getCurrency(), TouCengConstant.ONE);
                         if (TouCengConstant.ONE == result && !CollectionUtils.isEmpty(channelAccountNoList) && channelAccountNoList.size() == TouCengConstant.ONE) {
                             costWalletService.updateCostWallet(channelAccountNoList.get(TouCengConstant.ZERO), orderResDTO.getCurrency(), tempAmount, TouCengConstant.ACCOUNT_TYPE_OUT);
                         }else {
                             throw new BusinessException(EResultEnum.BAD_REQUEST.getCode(), "【付款订单回调Service】-error-修改【成本】余额失败，订单不存在或回调重复操作,订单号码:" + orderResDTO.getOrderNo());
                         }
                     }

                     redisCacheUtils.set(orderResDTO.getOrderNo(), String.valueOf(TouCengConstant.ONE), TouCengConstant.ORDER_LOCK_REDIS_TIME);
                 }
        	 
                 return null;
    	} catch (Exception e) {
			log.error("【付款订单回调Service】-error-[异常信息]:{}",e.getMessage());
			throw new BusinessException("【付款订单回调Service】-error-[异常信息]:"+e.getMessage());
		}finally {
		    distributedLock.releaseLock(TouCengConstant.ORDER_LOCK_REDIS_KEY);
		}
    }

    @Override
    public String payCreate(OrderCreateDTO dto) {


        // 订单列表判断
        if (null == dto || CollectionUtils.isEmpty(dto.getOrderList())) {
            log.info("【收款订单Service】-error-付款参数为空或订单列表为空");
            throw new BusinessException(EResultEnum.BAD_REQUEST.getCode(), "【收款订单Service】参数为空或订单列表为空");
        }

        if (dto.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            log.info("【收款订单Service】-error-订单金额不正确,订单信息:{}", JSON.toJSONString(dto));
            throw new BusinessException(EResultEnum.BAD_REQUEST.getCode(), "【收款订单Service】订单金额不正确，订单信息:"+JSON.toJSONString(dto));
        }

        try {
        	
	        	//获取锁失败
	        	if(!distributedLock.lock(TouCengConstant.ORDER_LOCK_REDIS_KEY)) {
	        		 log.info("【收款订单Service】-error-获取锁失败,订单信息:{}", JSON.toJSONString(dto));
	        		 throw new BusinessException(EResultEnum.REQUEST_REMOTE_ERROR.getCode(), "【收款订单Service】获取锁失败，订单信息:"+JSON.toJSONString(dto));
	        	}

                // 查询用户账户/并创建
                UserWallet userWallet = userWalletService.getUserWallet(dto.getUserId(), dto.getCurrency(),dto.getUserType(), dto.getUserCode(), dto.getAgentId());

                // 商户校验
                if (null == userWallet || (StringUtils.isNoneBlank(dto.getAgentId()) && !dto.getAgentId().equals(userWallet.getAgentId()))) {
                    log.info("【收款订单Service】-error-获取用户信息失败或用户代理商信息不匹配:{}", JSON.toJSONString(dto));
                    throw new BusinessException(EResultEnum.VALIDATE_ERROR.getCode(), "【收款订单Service】获取用户信息失败或用户代理商信息不匹配，订单信息:"+JSON.toJSONString(dto));
                }
                
                log.info("【收款订单Service】-info-未处理订单前商户信息，商户号码:{}，商户余额:{}", userWallet.getUserCode(),userWallet.getBalance());

                //手续费配置 费用类型-feeType【0-费率；1-固定额】
                int feeType = 0;
                BigDecimal floorAgentProfitAmount = null;//代理分润
                BigDecimal floorCostProfitAmount = null;//成本手续费
                BigDecimal retailProfitAmount = null;//零售手续费
                UserWalletLog userWalletLog = null;//用户账户变动明细
                BigDecimal tempAmount = null;
                BigDecimal agentShareAmount = BigDecimal.ZERO;//代理账户支出金额
                UserWalletLog userWalletLogTarget = null;
                UserWalletLog agentWalletLog = null;
                ChannelWalletLog channelWalletLogTarget = null;
                ChargeWalletLog chargeWalletLogTarget = null;
                for (OrderReqDTO orderReqDTO : dto.getOrderList()) {

                    log.info("【收款订单Service】-info- 付款订单信息:{}",JSON.toJSONString(orderReqDTO));

                    if (orderReqDTO.getOrderAmount().compareTo(BigDecimal.ZERO) <= 0) {
                        log.info("【收款订单Service】-error-订单金额不正确,订单信息:{}", JSON.toJSONString(orderReqDTO));
                        throw new BusinessException(EResultEnum.BAD_REQUEST.getCode(), "【收款订单Service】订单金额不正确,订单信息:" + JSON.toJSONString(orderReqDTO));
                    }
                    
                    if (redisCacheUtils.hasKey(orderReqDTO.getOrderNo())) {
                        log.info("【收款订单Service】-error-订单号码已存在,订单信息:{}", JSON.toJSONString(orderReqDTO));
                        throw new BusinessException(EResultEnum.BAD_REQUEST.getCode(), "【收款订单Service】订单号码已存在,订单信息:" + JSON.toJSONString(orderReqDTO));
                    }


                    feeType = orderReqDTO.getProfitRate().getFeeType();
                    log.info("【收款订单Service】-info-费用原始分成比列，扣款类型【内扣-0/外扣-1】:{},费用类型【0-费率；1-固定额】:{},代理底价:{},成本:{},手续费:{}",orderReqDTO.getWithdrawType(),feeType,orderReqDTO.getProfitRate().getFloorAgentProfit(), orderReqDTO.getProfitRate().getFloorCostProfit(),orderReqDTO.getProfitRate().getRetailProfit());
                    //费用类型-feeType【0-费率；1-固定额】
                    if (TouCengConstant.ZERO == feeType) {

                        //分润=订单金额*（零售费率-代理底价）
                        floorAgentProfitAmount = orderReqDTO.getOrderAmount().multiply(BigDecimal.valueOf(orderReqDTO.getProfitRate().getRetailProfit() - orderReqDTO.getProfitRate().getFloorAgentProfit())).multiply(TouCengConstant.TC_UNIT_THOUSAND);

                        //成本手续费=订单金额*渠道成本
                        floorCostProfitAmount = orderReqDTO.getOrderAmount().multiply(BigDecimal.valueOf(orderReqDTO.getProfitRate().getFloorCostProfit())).multiply(TouCengConstant.TC_UNIT_THOUSAND);

                        //零售手续费=订单金额*零售费率
                        retailProfitAmount = orderReqDTO.getOrderAmount().multiply(BigDecimal.valueOf(orderReqDTO.getProfitRate().getRetailProfit())).multiply(TouCengConstant.TC_UNIT_THOUSAND);

                    } else {
                        //分润=订单金额*（零售费率-代理底价）
                        floorAgentProfitAmount = BigDecimal.valueOf(orderReqDTO.getProfitRate().getRetailProfit() - orderReqDTO.getProfitRate().getFloorAgentProfit()).multiply(TouCengConstant.TC_UNIT_THOUSAND);

                        //成本手续费=订单金额*渠道成本
                        floorCostProfitAmount = BigDecimal.valueOf(orderReqDTO.getProfitRate().getFloorCostProfit()).multiply(TouCengConstant.TC_UNIT_THOUSAND);

                        //零售手续费=订单金额*零售费率
                        retailProfitAmount = BigDecimal.valueOf(orderReqDTO.getProfitRate().getRetailProfit()).multiply(TouCengConstant.TC_UNIT_THOUSAND);
                    }

                    log.info("【收款订单Service】-info-费用分成金额计算结果，分润:{},成本:{},手续费:{},订单编号:{},订单金额:{}",floorAgentProfitAmount, floorCostProfitAmount,retailProfitAmount,orderReqDTO.getOrderNo(),orderReqDTO.getOrderAmount());
                    
                    //用户账户金额扣款
                    userWalletService.updateUserWallet(dto.getUserId(), dto.getCurrency(), orderReqDTO.getOrderAmount().subtract(retailProfitAmount), TouCengConstant.ACCOUNT_TYPE_IN);
                    log.info("【收款订单Service】-success- 修改【商户账户:{}】余额成功-收入【订单金额:{}】-支出【手续费:{}】",userWallet.getUserCode(),orderReqDTO.getOrderAmount(),retailProfitAmount);

                    userWalletLog = new UserWalletLog();

                    //订单号
                    userWalletLog.setOrderNo(orderReqDTO.getOrderNo());
                    userWalletLog.setOrderAmount(orderReqDTO.getOrderAmount());
                    userWalletLog.setOrderType(dto.getOrderType());
                    userWalletLog.setUserType(userWallet.getUserType());
                    userWalletLog.setUserCode(userWallet.getUserCode());

                    //业务类型
                    userWalletLog.setProductCode(orderReqDTO.getProductCode());
                    //业务类型名称
                    userWalletLog.setProductName(orderReqDTO.getProductName());
                    userWalletLog.setCurrency(dto.getCurrency());

                    //用户支出金额
                    userWalletLog.setIncome(orderReqDTO.getOrderAmount());
                    //用户当前剩余金额
                    userWalletLog.setBalance(userWallet.getBalance().add(orderReqDTO.getOrderAmount()));
                    userWalletLog.setUserId(dto.getUserId());
                    //用户账户号
                    userWalletLog.setUserWalletNo(userWallet.getAccountNo());
                    //用户账单明细保存
                    userWalletLogService.save(userWalletLog);
                    log.info("【收款订单Service】-success- 保存【商户账户:{}】流水成功-收入-【订单金额:{}】",userWallet.getUserCode(),orderReqDTO.getOrderAmount());
                    
                    userWalletLogTarget = new UserWalletLog();
                    BeanUtils.copyProperties(userWalletLog, userWalletLogTarget);

                    userWalletLogTarget.setId(null);
                    userWalletLogTarget.setIncome(BigDecimal.ZERO);
                    userWalletLogTarget.setOutcome(retailProfitAmount);
                    //用户当前剩余金额
                    userWalletLogTarget.setBalance(userWalletLog.getBalance().subtract(retailProfitAmount));
                    //业务类型
                    userWalletLogTarget.setProductCode(TouCengConstant.RETAIL_PROFIT_CODE);
                    //业务类型名称
                    userWalletLogTarget.setProductName(TouCengConstant.RETAIL_PROFIT_NAME);
                    //用户账单手续费保存
                    userWalletLogService.save(userWalletLogTarget);
                    log.info("【收款订单Service】-success- 保存【商户账户:{}】流水成功-支出-【手续费:{}】",userWallet.getUserCode(),retailProfitAmount);

                    //渠道商户获取并创建
                    ChannelWallet channelWallet = channelWalletService.getChannelWallet(dto.getCurrency(), orderReqDTO.getChannelAccountNo(), orderReqDTO.getChannelAccountName(), orderReqDTO.getChannelName());
                    log.info("【收款订单Service】-success-获取渠道账户余额,渠道账户号:{}，渠道余额:{}",channelWallet.getChannelAccountNo(),channelWallet.getBalance());

                    
                    channelWalletService.updateChannelWallet(orderReqDTO.getChannelAccountNo(), dto.getCurrency(), orderReqDTO.getOrderAmount().subtract(floorCostProfitAmount), TouCengConstant.ACCOUNT_TYPE_IN);
                	log.info("【收款订单Service】-success- 修改【内部渠道账户:{}】余额成功-收入【订单金额:{}】-支出【成本:{}】",orderReqDTO.getChannelAccountNo(),orderReqDTO.getOrderAmount(),floorCostProfitAmount);

               	 
                    ChannelWalletLog channelWalletLog = new ChannelWalletLog();
                    channelWalletLog.setChannelAccountNo(orderReqDTO.getChannelAccountNo());
                    channelWalletLog.setOrderAmount(orderReqDTO.getOrderAmount());
                    channelWalletLog.setOrderType(dto.getOrderType());

                    //订单号
                    channelWalletLog.setOrderNo(orderReqDTO.getOrderNo());
                    //业务类型
                    channelWalletLog.setProductCode(orderReqDTO.getProductCode());
                    //业务类型名称
                    channelWalletLog.setProductName(orderReqDTO.getProductName());
                    channelWalletLog.setCurrency(dto.getCurrency());

                    //用户支出金额
                    channelWalletLog.setIncome(orderReqDTO.getOrderAmount());
                    //用户当前剩余金额
                    channelWalletLog.setBalance(channelWallet.getBalance().add(orderReqDTO.getOrderAmount()));
                    channelWalletLog.setStatus(TouCengConstant.ONE);
                    channelWalletLogService.saveChannelWalletLog(channelWalletLog);
                    log.info("【收款订单Service】-success- 保存【内部渠道账户:{}】流水成功-收入-【订单金额:{}】",orderReqDTO.getChannelAccountNo(),orderReqDTO.getOrderAmount());
                    
                    channelWalletLogTarget = new ChannelWalletLog();
                    BeanUtils.copyProperties(channelWalletLog, channelWalletLogTarget);
                    channelWalletLogTarget.setId(null);
                    channelWalletLogTarget.setIncome(BigDecimal.ZERO);
                    //用户支出金额
                    channelWalletLogTarget.setOutcome(floorCostProfitAmount);
                    //用户当前剩余金额
                    channelWalletLogTarget.setBalance(channelWalletLog.getBalance().subtract(floorCostProfitAmount));
                    //业务类型
                    channelWalletLogTarget.setProductCode(TouCengConstant.COST_PROFIT_CODE);
                    //业务类型名称
                    channelWalletLogTarget.setProductName(TouCengConstant.COST_PROFIT_NAME);
                    channelWalletLogService.saveChannelWalletLog(channelWalletLogTarget);
                    log.info("【收款订单Service】-success- 保存【内部渠道账户:{}】流水成功-支出-【成本:{}】",orderReqDTO.getChannelAccountNo(),floorCostProfitAmount);

                    //代理商分润
                    if (!CollectionUtils.isEmpty(orderReqDTO.getAgentShareList())) {

                        //代理商分润
                        for (AgentShareDTO agentShareDTO : orderReqDTO.getAgentShareList()) {

                            //【0-费率；1-固定额】
                            if (TouCengConstant.ZERO == agentShareDTO.getFeeType()) {
                                tempAmount = orderReqDTO.getOrderAmount().multiply(BigDecimal.valueOf(agentShareDTO.getAgentRate())).multiply(TouCengConstant.TC_UNIT_THOUSAND);
                            } else {
                                tempAmount = BigDecimal.valueOf(agentShareDTO.getAgentRate()).multiply(TouCengConstant.TC_UNIT_THOUSAND);
                            }

                            if (tempAmount.compareTo(BigDecimal.ZERO) > 0) {

                                UserWallet agentWallet = userWalletService.getUserWallet(agentShareDTO.getAgentId(), dto.getCurrency(), agentShareDTO.getUserType(),agentShareDTO.getAgentCode(), null);
                                userWalletService.updateUserWallet(agentShareDTO.getAgentId(), dto.getCurrency(), tempAmount, TouCengConstant.ACCOUNT_TYPE_IN);
                                log.info("【收款订单Service】-success- 修改【代理商户账户:{}】余额成功-收入【分润:{}】",userWallet.getUserCode(),tempAmount);
                                
                                agentWalletLog = new UserWalletLog();
                                //订单号
                                agentWalletLog.setOrderNo(orderReqDTO.getOrderNo());
                                agentWalletLog.setOrderAmount(orderReqDTO.getOrderAmount());
                                agentWalletLog.setOrderType(dto.getOrderType());

                                //用户账户号
                                agentWalletLog.setUserWalletNo(agentWallet.getAccountNo());
                                agentWalletLog.setUserType(agentWallet.getUserType());
                                agentWalletLog.setUserCode(agentWallet.getUserCode());
                                agentWalletLog.setUserId(agentWallet.getUserId());

                                //业务类型
                                agentWalletLog.setProductCode(TouCengConstant.AGENT_PROFIT_CODE);
                                //业务类型名称
                                agentWalletLog.setProductName(TouCengConstant.AGENT_PROFIT_NAME);
                                agentWalletLog.setCurrency(dto.getCurrency());

                                //用户支出金额
                                agentWalletLog.setIncome(tempAmount);
                                //用户当前剩余金额
                                agentWalletLog.setBalance(agentWallet.getBalance().add(tempAmount));

                                userWalletLogService.save(agentWalletLog);
                                log.info("【收款订单Service】-success- 保存【代理商户账户:{}】流水成功-收入-【分润:{}】",userWallet.getUserCode(),tempAmount);
                                agentShareAmount = agentShareAmount.add(tempAmount);
                            }

                        }

                    }

                    //代理商
                    if (StringUtils.isNoneBlank(userWallet.getAgentId())) {
                        tempAmount = floorAgentProfitAmount.subtract(agentShareAmount);
                        if (tempAmount.compareTo(BigDecimal.ZERO) > 0) {

                            UserWallet agentWallet = userWalletService.getUserWallet(userWallet.getAgentId(), dto.getCurrency(),TouCengConstant.ONE, dto.getAgentCode(), null);
                            userWalletService.updateUserWallet(agentWallet.getUserId(), dto.getCurrency(), tempAmount, TouCengConstant.ACCOUNT_TYPE_IN);
                            log.info("【收款订单Service】-success- 修改【代理商户账户:{}】余额成功-收入【分润:{}】",userWallet.getUserCode(),tempAmount);

                            agentWalletLog = new UserWalletLog();
                            
                            //订单号
                            agentWalletLog.setOrderNo(orderReqDTO.getOrderNo());
                            agentWalletLog.setOrderAmount(orderReqDTO.getOrderAmount());
                            agentWalletLog.setOrderType(dto.getOrderType());

                            //用户账户号
                            agentWalletLog.setUserWalletNo(agentWallet.getAccountNo());
                            agentWalletLog.setUserType(agentWallet.getUserType());
                            agentWalletLog.setUserCode(agentWallet.getUserCode());
                            agentWalletLog.setUserId(agentWallet.getUserId());

                            //业务类型
                            agentWalletLog.setProductCode(TouCengConstant.AGENT_PROFIT_CODE);
                            //业务类型名称
                            agentWalletLog.setProductName(TouCengConstant.AGENT_PROFIT_NAME);
                            agentWalletLog.setCurrency(dto.getCurrency());

                            //用户支出金额
                            agentWalletLog.setIncome(tempAmount);
                            //用户当前剩余金额
                            agentWalletLog.setBalance(agentWallet.getBalance().add(tempAmount));

                            userWalletLogService.save(agentWalletLog);
                            log.info("【收款订单Service】-success- 保存【代理商户账户:{}】流水成功-收入-【分润:{}】",userWallet.getUserCode(),tempAmount);

                        }
                    }


                    //内部手续费
                    ChargeWallet chargeWallet = chargeWalletService.getChargeWallet(dto.getCurrency(), orderReqDTO.getChannelAccountNo());
                    chargeWalletService.updateChargeWallet(orderReqDTO.getChannelAccountNo(), dto.getCurrency(), retailProfitAmount.subtract(floorAgentProfitAmount), TouCengConstant.ACCOUNT_TYPE_IN);
                    log.info("【收款订单Service】-success- 修改【手续费账户:{}】余额成功-收入【手续费:{}】- 支出【分润:{}】",orderReqDTO.getChannelAccountNo(),retailProfitAmount,floorAgentProfitAmount);
                    
                    
                    ChargeWalletLog chargeWalletLog = new ChargeWalletLog();
                    chargeWalletLog.setChannelAccountNo(orderReqDTO.getChannelAccountNo());
                    chargeWalletLog.setOrderAmount(orderReqDTO.getOrderAmount());
                    chargeWalletLog.setOrderType(dto.getOrderType());

                    //订单号
                    chargeWalletLog.setOrderNo(orderReqDTO.getOrderNo());
                    //业务类型
                    chargeWalletLog.setProductCode(TouCengConstant.RETAIL_PROFIT_CODE);
                    //业务类型名称
                    chargeWalletLog.setProductName(TouCengConstant.RETAIL_PROFIT_NAME);
                    chargeWalletLog.setCurrency(dto.getCurrency());
                    chargeWalletLog.setChargeWalletNo(chargeWallet.getAccountNo());
                    //用户支出金额
                    chargeWalletLog.setIncome(retailProfitAmount);
                    //用户当前剩余金额
                    chargeWalletLog.setBalance(chargeWallet.getBalance().add(retailProfitAmount));
                    chargeWalletLogService.saveChargeWalletLog(chargeWalletLog);
                    log.info("【收款订单Service】-success- 保存【手续费账户:{}】流水成功-收入-【手续费:{}】",orderReqDTO.getChannelAccountNo(),retailProfitAmount);

                    chargeWalletLogTarget = new ChargeWalletLog();
                    BeanUtils.copyProperties(chargeWalletLog, chargeWalletLogTarget);

                    chargeWalletLogTarget.setId(null);
                    chargeWalletLogTarget.setIncome(BigDecimal.ZERO);
                    //用户支出金额
                    chargeWalletLogTarget.setOutcome(floorAgentProfitAmount);
                    //用户当前剩余金额
                    chargeWalletLogTarget.setBalance(chargeWalletLog.getBalance().subtract(floorAgentProfitAmount));
                    //业务类型
                    chargeWalletLogTarget.setProductCode(TouCengConstant.AGENT_PROFIT_CODE);
                    //业务类型名称
                    chargeWalletLogTarget.setProductName(TouCengConstant.AGENT_PROFIT_NAME);
                    chargeWalletLogService.saveChargeWalletLog(chargeWalletLogTarget);
                    log.info("【收款订单Service】-success- 保存【手续费账户:{}】流水成功-支出-【分润:{}】",orderReqDTO.getChannelAccountNo(),floorAgentProfitAmount);


                    //成本手续费
                    CostWallet costWallet = costWalletService.getCostWallet(dto.getCurrency(), orderReqDTO.getChannelAccountNo());
                    costWalletService.updateCostWallet(orderReqDTO.getChannelAccountNo(), dto.getCurrency(), floorCostProfitAmount, TouCengConstant.ACCOUNT_TYPE_IN);
                    log.info("【收款订单Service】-success- 修改【成本账户:{}】余额成功-收入【成本:{}】",orderReqDTO.getChannelAccountNo(),floorCostProfitAmount);
                    
                    CostWalletLog costWalletLog = new CostWalletLog();
                    costWalletLog.setChannelAccountNo(orderReqDTO.getChannelAccountNo());

                    //订单号
                    costWalletLog.setOrderNo(orderReqDTO.getOrderNo());
                    costWalletLog.setOrderAmount(orderReqDTO.getOrderAmount());
                    costWalletLog.setOrderType(dto.getOrderType());
                    //业务类型
                    costWalletLog.setProductCode(TouCengConstant.COST_PROFIT_CODE);
                    //业务类型名称
                    costWalletLog.setProductName(TouCengConstant.COST_PROFIT_NAME);
                    costWalletLog.setCurrency(dto.getCurrency());
                    costWalletLog.setCostWalletNo(costWallet.getAccountNo());

                    //用户支出金额
                    costWalletLog.setIncome(floorCostProfitAmount);
                    //用户当前剩余金额
                    costWalletLog.setBalance(costWallet.getBalance().add(floorCostProfitAmount));
                    costWalletLogService.saveCostWalletLog(costWalletLog);
                    log.info("【收款订单Service】-success- 保存【成本账户:{}】流水成功-收入【成本:{}】",orderReqDTO.getChannelAccountNo(),floorCostProfitAmount);
                    
                    redisCacheUtils.set(orderReqDTO.getOrderNo(), String.valueOf(TouCengConstant.ONE), TouCengConstant.ORDER_LOCK_REDIS_TIME);
                }
                return String.valueOf(userWalletLogTarget.getBalance());

    	} catch (Exception e) {
			log.error("【收款订单Service】-error-[异常信息]:{}",e.getMessage());
			throw new BusinessException("【收款订单Service】-error-[异常信息]:"+e.getMessage());
		}finally {
		    distributedLock.releaseLock(TouCengConstant.ORDER_LOCK_REDIS_KEY);
		}

    }

}
