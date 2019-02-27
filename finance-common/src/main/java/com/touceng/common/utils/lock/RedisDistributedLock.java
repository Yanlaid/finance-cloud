package com.touceng.common.utils.lock;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @classDesc: 类描述: 基于redis实现分布式锁
 * @author Wu,Hua-Zheng
 * @createTime 2018年9月8日 上午2:59:57
 * @version v1.0.0
 * @copyright: 上海投嶒网络技术有限公司
 */
@Component
public class RedisDistributedLock extends AbstractDistributedLock {

	private final Logger logger = LoggerFactory.getLogger(RedisDistributedLock.class);

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 获取锁
	 */
	@Override
	public boolean lock(String key, long expire, int retryTimes, long sleepMillis) {
		
		// 获取连接
		RedisConnectionFactory connectionFactory = stringRedisTemplate.getConnectionFactory();
		RedisConnection redisConnection = connectionFactory.getConnection();
		boolean result = false;
		// 如果获取锁失败，按照传入的重试次数进行重试
		while((!result) && retryTimes-- > 0){
			
			if (redisConnection.setNX(key.getBytes(), key.getBytes())) {
				redisConnection.expire(key.getBytes(), expire);
				// 返回value值，用于释放锁时间确认
				RedisConnectionUtils.releaseConnection(redisConnection, connectionFactory);
				logger.debug("【redis分布式锁】-info - 获取锁成功：" + key);
				return true;
			}
			// 返回-1代表key没有设置超时时间，为key设置一个超时时间
			if (redisConnection.ttl(key.getBytes()) == -1) {
				redisConnection.expire(key.getBytes(), expire);
			}
			
			try {
				logger.debug("【redis分布式锁】 - debug- 获取锁失败,重试次数:{}" ,retryTimes);
				Thread.sleep(sleepMillis);
			} catch (InterruptedException e) {
				logger.error("【redis分布式锁】- error- 获取到分布式锁：线程中断！,[异常信息]:{}", e,e);
				return false;
			}
			
		}
		RedisConnectionUtils.releaseConnection(redisConnection, connectionFactory);
		return false;
	}

	/**
	 * 释放锁
	 */
	@Override
	public boolean releaseLock(String key) {
		RedisConnectionFactory connectionFactory = stringRedisTemplate.getConnectionFactory();
		RedisConnection redisConnection = connectionFactory.getConnection();
		boolean releaseFlag = false;
		while (true) {
			try {
				// 监视lock，准备开始事务
				redisConnection.watch(key.getBytes());
				// 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
				byte[] valueBytes = redisConnection.get(key.getBytes());
				if (valueBytes == null) {
					redisConnection.unwatch();
					releaseFlag = false;
					break;
				}
				if (key.equals(new String(valueBytes))) {
					redisConnection.multi();
					redisConnection.del(key.getBytes());
					List<Object> results = redisConnection.exec();
					if (results == null) {
						continue;
					}
					releaseFlag = true;
				}
				redisConnection.unwatch();
				break;
			} catch (Exception e) {
				logger.error("【redis分布式锁】- error- 释放锁异常,[异常信息]:{}", e,e);
			}
		}
		RedisConnectionUtils.releaseConnection(redisConnection, connectionFactory);
		return releaseFlag;
	}

}
