package com.touceng.common.rabbitMq;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: RabbitMQ配置
 * @createTime 2018年8月9日 下午9:52:13
 * @copyright: 上海投嶒网络技术有限公司
 */
@Configuration
public class RabbitMQConfig {

    //操作记录
    public final static String TC_MQ_OPERATE_RECORD_QUEUE = "tc_mq_operate_record_queue";


    @Bean
    public Queue operateRecordQueue() {
        return new Queue(RabbitMQConfig.TC_MQ_OPERATE_RECORD_QUEUE);
    }

}