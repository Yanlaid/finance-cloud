package com.touceng.domain.constant;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.touceng.domain.entity.finance.AgentWallet;
import com.touceng.domain.entity.finance.AgentWalletLog;
import com.touceng.domain.entity.finance.ChannelWallet;
import com.touceng.domain.entity.finance.ChannelWalletLog;
import com.touceng.domain.entity.finance.ChargeWallet;
import com.touceng.domain.entity.finance.ChargeWalletLog;
import com.touceng.domain.entity.finance.CostWallet;
import com.touceng.domain.entity.finance.CostWalletLog;
import com.touceng.domain.entity.finance.UserWallet;
import com.touceng.domain.entity.finance.UserWalletLog;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: 常量类
 * @createTime 2018年7月3日 上午9:33:11
 * @copyright: 上海投嶒网络技术有限公司
 */
public class TouCengDataConstant {

	public static Map<String, UserWallet> userMap = new ConcurrentHashMap<>();
	public static Map<String, ChannelWallet> channelMap = new ConcurrentHashMap<>();
	public static Map<String, CostWallet> costMap = new ConcurrentHashMap<>();
	public static Map<String, ChargeWallet> chargeMap = new ConcurrentHashMap<>();

	public static List<UserWalletLog> userLogList = new LinkedList<>();
	public static List<ChannelWalletLog> channelLogList = new LinkedList<>();
	public static List<CostWalletLog> costLogList = new LinkedList<>();
	public static List<ChargeWalletLog> chargeLogList = new LinkedList<>();

}
