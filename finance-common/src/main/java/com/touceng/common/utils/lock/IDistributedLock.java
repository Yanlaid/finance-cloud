package com.touceng.common.utils.lock;

/**
 * @classDesc: 类描述: 分布式锁定义接口
 * @author Wu,Hua-Zheng
 * @createTime 2018年9月8日 上午2:55:38
 * @version v1.0.0
 * @copyright: 上海投嶒网络技术有限公司
 */
public interface IDistributedLock {
	
	public static final long TIMEOUT_MILLIS = 10;//单位秒

	public static final int RETRY_TIMES = Integer.MAX_VALUE;

	public static final long SLEEP_MILLIS = 25;//单位毫秒

	public boolean lock(String key);

	public boolean lock(String key, int retryTimes);

	public boolean lock(String key, int retryTimes, long sleepMillis);

	public boolean lock(String key, long expire);

	public boolean lock(String key, long expire, int retryTimes);

	public boolean lock(String key, long expire, int retryTimes, long sleepMillis);

	public boolean releaseLock(String key);
}
