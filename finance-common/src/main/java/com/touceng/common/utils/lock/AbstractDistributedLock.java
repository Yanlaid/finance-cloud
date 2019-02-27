package com.touceng.common.utils.lock;

/**
 * @classDesc: 类描述: 分布式锁接口实现
 * @author Wu,Hua-Zheng
 * @createTime 2018年9月8日 上午2:56:33
 * @version v1.0.0
 * @copyright: 上海投嶒网络技术有限公司
 */
public abstract class AbstractDistributedLock implements IDistributedLock {

	@Override
	public boolean lock(String key) {
		return lock(key, TIMEOUT_MILLIS, RETRY_TIMES, SLEEP_MILLIS);
	}

	@Override
	public boolean lock(String key, int retryTimes) {
		return lock(key, TIMEOUT_MILLIS, retryTimes, SLEEP_MILLIS);
	}

	@Override
	public boolean lock(String key, int retryTimes, long sleepMillis) {
		return lock(key, TIMEOUT_MILLIS, retryTimes, sleepMillis);
	}

	@Override
	public boolean lock(String key, long expire) {
		return lock(key, expire, RETRY_TIMES, SLEEP_MILLIS);
	}

	@Override
	public boolean lock(String key, long expire, int retryTimes) {
		return lock(key, expire, retryTimes, SLEEP_MILLIS);
	}

}
