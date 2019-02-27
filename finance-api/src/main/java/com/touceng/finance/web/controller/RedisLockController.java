package com.touceng.finance.web.controller;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.touceng.common.utils.lock.IDistributedLock;



@RestController
@RequestMapping("redislock")
public class RedisLockController {
	
	private final Logger logger = LoggerFactory.getLogger(RedisLockController.class);
	
	@Resource
	private IDistributedLock distributedLock;
	
	private int num = 0;
	
	@RequestMapping("lock")
	public String lock(){
		for(int i=0; i<20; i++){
			new RedisLockThread().start();
		}
		for(int i=0; i<20; i++){
			new RedisLockThread2().start();
		}
		for(int i=0; i<10; i++){
			new RedisLockThread3().start();
		}
		for(int i=0; i<10; i++){
			new RedisLockThread4().start();
		}
		for(int i=0; i<10; i++){
			new RedisLockThread2().start();
		}
		for(int i=0; i<20; i++){
			new RedisLockThread().start();
		}
		for(int i=0; i<20; i++){
			new RedisLockThread3().start();
		}
		return "ResultMap.buildSuccess()";
	}
	
	class RedisLockThread extends Thread {

		@Override
		public void run() {
			String key = "wuhuazheng";
			if(distributedLock.lock(key)) {
				logger.info(num+" get lock success : +2" + key+(num +=2));
				try {
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					logger.error("exp", e);
				} finally {
				    while(!distributedLock.releaseLock(key)) {
				    	logger.info("release lock failed: " + key);
				    }
				    logger.info("release lock success : " + key);
				}
			}else {
				logger.info("get lock failed : " + key);

			}
			
		}
	}
	
	
	class RedisLockThread2 extends Thread {

		@Override
		public void run() {
			String key = "wuhuazheng";
			if(distributedLock.lock(key)) {
				logger.info(num+" get lock success : -1" + key+(num -=1));
				try {
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					logger.error("exp", e);
				} finally {
				    while(!distributedLock.releaseLock(key)) {
				    	logger.info("release lock failed: " + key);
				    }
				    logger.info("release lock success : " + key);
				}
			}else {
				logger.info("get lock failed : " + key);

			}
			
		}
	}
	
	
	class RedisLockThread3 extends Thread {

		@Override
		public void run() {
			String key = "wuhuazheng";
			if(distributedLock.lock(key)) {
				logger.info(num+" get lock success : +3" + key+(num +=3));
				try {
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					logger.error("exp", e);
				} finally {
				    while(!distributedLock.releaseLock(key)) {
				    	logger.info("release lock failed: " + key);
				    }
				    logger.info("release lock success : " + key);
				}
			}else {
				logger.info("get lock failed : " + key);

			}
			
		}
	}
	
	
	class RedisLockThread4 extends Thread {

		@Override
		public void run() {
			String key = "wuhuazheng";
			if(distributedLock.lock(key)) {
				logger.info(num+" get lock success : +1" + key+(num +=1));
				try {
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					logger.error("exp", e);
				} finally {
				    while(!distributedLock.releaseLock(key)) {
				    	logger.info("release lock failed: " + key);
				    }
				    logger.info("release lock success : " + key);
				}
			}else {
				logger.info("get lock failed : " + key);

			}
			
		}
	}
	

}
