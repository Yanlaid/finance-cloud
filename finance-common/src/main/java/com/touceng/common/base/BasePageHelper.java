package com.touceng.common.base;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: 分页查询参数
 * @createTime 2018年6月29日 上午10:58:01
 * @copyright: 上海投嶒网络技术有限公司
 */
@Data
public class BasePageHelper implements Serializable {


    private static final long serialVersionUID = -7068361700500939655L;

    /**
     * 排序字段
     */
    @ApiModelProperty( value = "排序字段")
    public String sort="id";

    /**
     * 排序方向
     */
    @ApiModelProperty( value = "排序方向 ")
    public String order="desc";

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码")
    public int pageNum = 1;

    /**
     * 每页条数
     */
    @ApiModelProperty(value = "每页条数")
    public int pageSize = 30;

}
