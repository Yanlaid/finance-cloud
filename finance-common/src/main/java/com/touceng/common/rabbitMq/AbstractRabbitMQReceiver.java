package com.touceng.common.rabbitMq;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: RabbitMQ接收服务
 * @createTime 2018年8月9日 下午9:51:25
 * @copyright: 上海投嶒网络技术有限公司
 */
public abstract class AbstractRabbitMQReceiver {

    public abstract void receiveQueueName(String msg);

}
