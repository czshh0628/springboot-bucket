package czs.coding.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 配置类
 *
 * @author czs
 */
@EnableRabbit
public class RabbitMqConfig {
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 配置使用json的方式序列化对象
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * PostConstruct: 当RabbitMqConfig对象创建完再执行该方法
     * 定制RabbitTemplate
     * 1. MQ服务器收到消息就回调
     * 1. spring.rabbitmq.publisher-confirms=true
     * 2. 设置回调确认confirmCallback
     * 2. 消息正确抵达队列进行回调
     * 1. spring.rabbitmq.publisher-returns=true
     * 2. spring.rabbitmq.template.mandatory=true
     * 3. 设置回调确认returnCallback
     */
    @PostConstruct
    public void initRabbitTemplate() {
        // 设置MQ服务器收到消息回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             * 只要消息抵达MQ服务器ack就为true
             * @param correlationData：当前消息的唯一关联数据（这个是消息的唯一id）即发送时传的CorrelationData参数
             * @param b：ack，消息是否成功还是失败
             * @param s：失败的原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                System.out.println("correlationData: " + correlationData);
                System.out.println("ack: " + b);
                System.out.println("s: " + s);
            }
        });
        // 设置消息抵达队列回调
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             * 只要消息没有投递给指定的队列，就触发这个失败回调
             * @param message：投递失败的消息详细信息
             * @param i：回复的状态码
             * @param s：回复的文本内容
             * @param s1：当时这个消息发送给哪个交换机
             * @param s2：当时这个消息发送给哪个路由键
             */
            @Override
            public void returnedMessage(Message message, int i, String s, String s1, String s2) {
                System.out.println("fail message: " + message);
                System.out.println("i: " + i);
                System.out.println("s: " + s);
                System.out.println("s1: " + s1);
                System.out.println("s2: " + s2);
            }
        });
    }

}
