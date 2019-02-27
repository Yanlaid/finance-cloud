package com.touceng.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.touceng.common.constant.TCConfigConstant;
import com.touceng.common.utils.SnowFlakeUtils;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: SnowFlake配置
 * @createTime 2018年7月14日 上午10:08:47
 * @copyright: 上海投嶒网络技术有限公司
 */
@Configuration
public class SnowFlakeConfguration {

	/**
	 * 每一部分占用的位数
	 */
	private final static long MACHINE_BIT = 5; // 机器标识占用的位数
	private final static long DATACENTER_BIT = 5;// 数据中心占用的位数

	private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
	private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);

	@Bean(name = "snowFlakeUtils")
	public SnowFlakeUtils processSnowFlakeBean() {
		if (TCConfigConstant.datacenter_code > MAX_DATACENTER_NUM || TCConfigConstant.datacenter_code < 0) {
			throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
		}
		if (TCConfigConstant.machine_code > MAX_MACHINE_NUM || TCConfigConstant.machine_code < 0) {
			throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
		}
		SnowFlakeUtils snowFlake = new SnowFlakeUtils();
		snowFlake.setDatacenterId(TCConfigConstant.datacenter_code);
		snowFlake.setMachineId(TCConfigConstant.machine_code);
		return snowFlake;
	}

}
