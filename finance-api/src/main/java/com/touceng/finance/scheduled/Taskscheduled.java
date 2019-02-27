//package com.touceng.finance.scheduled;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import com.touceng.common.utils.DateToolUtils;
//import com.touceng.domain.vo.SyncAgentVO;
//import com.touceng.domain.vo.SyncChannelVO;
//import com.touceng.domain.vo.SyncUserVO;
//import com.touceng.finance.component.SyncDataComponent;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Component
//public class Taskscheduled {
//
//	@Autowired
//	private SyncDataComponent syncDataComponent;
//
//	/**
//	 * @methodDesc: 方法描述:同步系统用户数据
//	 * @author Wu,Hua-Zheng
//	 * @createTime 2018年8月27日 下午9:38:13
//	 * @version v1.0.0
//	 */
//	@Scheduled(cron = "0 0 0/5 * * *")
//	public void syncUserdataJob() {
//		// 接口消费时间
//		ThreadLocal<Long> startTime = new ThreadLocal<Long>();
//		startTime.set(System.currentTimeMillis());
//		try {
//			List<SyncUserVO> userVOlist = syncDataComponent.syncUserdataHttp();
//			if (CollectionUtils.isEmpty(userVOlist)) {
//				return;
//			}
//
//		} catch (Exception e) {
//			log.error("syncUserdataJob，同步用户数据异常:{}", e, e);
//		}
//
//		log.debug("=====>>>>>使用cron-syncUserdataJob同步用户数据结束，当前时间:{}，花费时间:{}", DateToolUtils.getLongDateStr(),
//				String.valueOf(System.currentTimeMillis() - startTime.get()));
//	}
//
//	/**
//	 * @methodDesc: 方法描述:同步系统内部渠道数据
//	 * @author Wu,Hua-Zheng
//	 * @createTime 2018年8月27日 下午9:38:13
//	 * @version v1.0.0
//	 */
//	@Scheduled(cron = "0 0 0/5 * * *")
//	public void syncChanneldataJob() {
//
//		// 接口消费时间
//		ThreadLocal<Long> startTime = new ThreadLocal<Long>();
//		startTime.set(System.currentTimeMillis());
//		try {
//			List<SyncChannelVO> channelVOlist = syncDataComponent.syncChanneldataHttp();
//			if (CollectionUtils.isEmpty(channelVOlist)) {
//				return;
//			}
//
//		} catch (Exception e) {
//			log.error("syncChanneldataJob，同步系统内部渠道数据异常:{}", e, e);
//		}
//
//		log.debug("=====>>>>>使用cron-syncChanneldataJob同步系统内部渠道数据结束，当前时间:{}，花费时间:{}", DateToolUtils.getLongDateStr(),
//				String.valueOf(System.currentTimeMillis() - startTime.get()));
//	}
//
//	/**
//	 * @methodDesc: 方法描述:同步系统代理数据
//	 * @author Wu,Hua-Zheng
//	 * @createTime 2018年8月27日 下午9:38:13
//	 * @version v1.0.0
//	 */
//	@Scheduled(cron = "0 0 0/5 * * *")
//	public void syncAgentdataJob() {
//		// 接口消费时间
//		ThreadLocal<Long> startTime = new ThreadLocal<Long>();
//		startTime.set(System.currentTimeMillis());
//		try {
//			List<SyncAgentVO> agentVOlist = syncDataComponent.syncAgentdataHttp();
//			if (CollectionUtils.isEmpty(agentVOlist)) {
//				return;
//			}
//
//		} catch (Exception e) {
//			log.error("syncAgentdataJob，同步系统代理数据异常:{}", e, e);
//		}
//
//		log.debug("=====>>>>>使用cron-syncAgentdataJob同步系统代理数据结束，当前时间:{}，花费时间:{}", DateToolUtils.getLongDateStr(),
//				String.valueOf(System.currentTimeMillis() - startTime.get()));
//	}
//
//}