package com.touceng.common.log;

import lombok.Data;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 类描述: 操作日志
 * @createTime 2018年8月29日 下午2:24:46
 * @copyright: 上海投嶒网络技术有限公司
 */
@Data
public class OperateLog {

    private String content;//请求参数
    private String userCode;//商户号
    private String module = "账户流水查询";//模块


    public OperateLog(String content, String userCode) {
        this.content = content;
        this.userCode = userCode;
    }
}
