package com.touceng.domain.dto.order;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 类描述: 代理商分润
 * @createTime 2018年8月29日 下午2:24:46
 * @copyright: 上海投嶒网络技术有限公司
 */
@Data
public class AgentShareDTO implements Serializable {

    private static final long serialVersionUID = -6349817970240024740L;

    public Integer userType=0;//商户类型：0 普通商户，1 代理商户，2 子商户
    private String agentId;// 代理商ID string
    private String agentCode;// 代理商商户号 string
    private double agentRate;// 分润空间比例 number
    private int feeType;// 费用类型 number 【0-费率；1-固定额】
}
