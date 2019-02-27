package com.touceng.domain.sync.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SyncProfitShareDTO {

	public String uid;// user id

	public String companyName;// 分润账户公司名称

	public String email;// 分润账户email

	public BigDecimal percent;// 分成比例

	public String currency = "RMB";// 货币类型

	public String userProductId;

}
