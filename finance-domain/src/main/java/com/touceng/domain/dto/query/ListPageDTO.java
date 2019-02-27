package com.touceng.domain.dto.query;

import com.touceng.common.base.BasePageHelper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "分页查询参数", description = "分页查询参数")
public class ListPageDTO extends BasePageHelper {

	private static final long serialVersionUID = 6439104620617306771L;

	@ApiModelProperty(value = "状态")
	private Integer status;

	@ApiModelProperty(value = "商户号码")
	private String code;

	@ApiModelProperty(value = "订单号码")
	private String orderNo;

	@ApiModelProperty(value = "商户名称")
	private String name;

	@ApiModelProperty(value = "开始时间-yyyy-MM-dd HH:mm:ss")
	private String startTime;

	@ApiModelProperty(value = "结束时间-yyyy-MM-dd HH:mm:ss")
	private String endTime;

	@ApiModelProperty(value = "交易时间-yyyy-MM-dd")
	private String createDate;

}
