package com.touceng.common.constant;

import java.math.BigDecimal;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: 常量类
 * @createTime 2018年7月3日 上午9:33:11
 * @copyright: 上海投嶒网络技术有限公司
 */
public class TouCengConstant {

	public static final int TWO = 2;
	public static final int ONE = 1;
	public static final int ZERO = 0;

	public static final int MAX_EXCEL_PAGE_SIZE = 65535;



	public static final BigDecimal TC_UNIT_THOUSAND = BigDecimal.valueOf(0.001);

	public static final String AGENT_PROFIT_CODE = "dodo_profit_001";// 代理分润
	public static final String AGENT_PROFIT_NAME = "分润";// 代理分润

	public static final String COST_PROFIT_CODE = "dodo_profit_002";// 成本手续费
	public static final String COST_PROFIT_NAME = "成本手续费";// 成本手续费

	public static final String RETAIL_PROFIT_CODE = "dodo_profit_003";// 零售手续费
	public static final String RETAIL_PROFIT_NAME = "零售手续费";// 零售手续费

	// 账户收入
	public static final BigDecimal ACCOUNT_TYPE_IN = BigDecimal.valueOf(1);
	// 账户支出
	public static final BigDecimal ACCOUNT_TYPE_OUT = BigDecimal.valueOf(-1);

	public static final String EXCEL_TITLES = "titles";
	public static final String EXCEL_ATTRS = "attrs";

	// ID 参数名
	public static final String FIELD_ID_PARAM = "id";

	// 对返回数据进行降序排序
	public static final String SQL_ORDER_DESC = "desc";

	// 对返回数据进行升序排序
	public static final String SQL_ORDER_ASC = "asc";

	// 创建时间 参数名
	public static final String FIELD_CREATE_TIME_PARAM = "createTime";

	// 关联ID 参数名
	public static final String FIELD_RELATION_ID_PARAM = "relationId";

	// 关联类型 参数名
	public static final String FIELD_RELATION_TYPE_PARAM = "relationType";

	// isAvailable 参数名
	public static final String FIELD_IS_AVAILABLE_PARAM = "isAvailable";

	// version 参数名
	public static final String FIELD_VERSION_PARAM = "version";

	// type 参数名
	public static final String FIELD_TYPE_PARAM = "type";

	// status 参数名
	public static final String FIELD_STATUS_PARAM = "status";

	// 订单redis锁定
	//public static final String ORDER_LOCK_REDIS_KEY = "tc:order:lock:%s";
	
	// 订单redis锁定
	public static final String ORDER_LOCK_REDIS_KEY = "tc:order:lock";

	// 订单redis有效时长 分
	public static final int ORDER_LOCK_REDIS_TIME = 20 * 60;

	/**
	 * 获取代理/用户数据sql
	 */
	public static final String TC_SQL_USER_AGENT = "SELECT  ut.user_code as userCode,ut.agent_id as agentId,ut.user_type as userType,ut.id as userId,ut.register_time as createTime from user_t ut ";

	/**
	 * 获取内部渠道账户sql
	 */
	public static final String TC_SQL_CHANNEL = "select cat.id as channelAccountId,cat.account_no as channelAccountNo,cat.`name` as channelAccountName,cat.email,pct.`name` as channelName from channel_account_t cat left JOIN pay_channel_t pct ON cat.channel_id = pct.id where cat.`status` = 1";

	/**
	 * 收款订单-0
	 */
	public static final String TC_SQL_TABLE_ORDER_PAY = "select 0 as orderType,ot.submit_time as createTime,ot.channel_account_id as channelAccountId,ot.channel_id as channelId,  "
			+ "ot.order_no as orderNo,ot.product_code as productCode,pt.`name` as productName,ut.id as userId,ot.user_code as userCode,  "
			+ "ot.submit_amount  as orderAmount,ppt.fee  as costFee,ppt.fee_type as costFeeType,  "
			+ "uppt1.fee as chargeFee,uppt1.fee_type as chargeFeeType,  "
			+ "uppt1.rebate as agentRebate,uppt1.id as userProductId  FROM  pay_order_t ot  "
			+ "left join user_t ut on ot.user_code = ut.user_code  "
			+ "left join pay_product_t pt on ot.product_code = pt.`code`  "
			+ "left join pay_pipe_t ppt ON ot.pipe_id = ppt.id  "
			+ "left join user_pay_product_t uppt1 on ppt.product_id= uppt1.pay_product_id and ut.id = uppt1.uid  "
			+ "where ot.`status` = 1";

	/**
	 * 付款订单-1
	 */

	public static final String TC_SQL_ORDER_WITHDRAW = "select 1 as orderType,ot.submit_time as createTime,ot.channel_account_id as channelAccountId,ot.channel_id as channelId,  "
			+ "ot.order_no as orderNo,ot.product_code as productCode,pt.`name` as productName,ut.id as userId,ot.user_code as userCode,  "
			+ "ot.submit_amount as orderAmount,ppt.fee  as costFee,ppt.fee_type as costFeeType,  "
			+ "uppt1.fee as chargeFee,uppt1.fee_type as chargeFeeType,  "
			+ "uppt1.rebate as agentRebate,uppt1.id as userProductId  FROM  receipt_order_t ot  "
			+ "left join user_t ut on ot.user_code = ut.user_code  "
			+ "left join pay_product_t pt on ot.product_code = pt.`code`  "
			+ "left join pay_pipe_t ppt ON ot.pipe_id = ppt.id  "
			+ "left join user_pay_product_t uppt1 on ppt.product_id= uppt1.pay_product_id and ut.id = uppt1.uid  "
			+ "where ot.`status` = 1";

	/**
	 * 获取分润账户数据
	 */
	public static final String TC_SQL_PROFIT_SHARE = "select percent,uid from profit_share_t where effective_date <= now() and user_product_id  = #{param}";

}
