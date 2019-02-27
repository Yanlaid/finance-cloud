package com.touceng.common.rabbitMq;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: RabbitMQ消息发送
 * @createTime 2018年8月9日 下午9:50:46
 * @copyright: 上海投嶒网络技术有限公司
 */
@Component
public class RabbitMQSender {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AmqpTemplate amqpTemplate;


    public void send(String queue, String msg) {
        log.debug("====>>>消息推送：queue:{},msg :{}", queue, msg);
        this.amqpTemplate.convertAndSend(queue, msg);
    }


}
