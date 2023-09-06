package czs.coding.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 死信队列配置
 *
 * @author czs
 */
@Configuration
public class DeadQueueConfig {
    //普通交换机名称
    private static final String NORMAL_EXCHANGE = "normal_exchange";
    //死信交换机名称
    private static final String DEAD_EXCHANGE = "dead_exchange";

    // 声明死信交换机
    @Bean(DEAD_EXCHANGE)
    public DirectExchange deadExchange() {
        return new DirectExchange(DEAD_EXCHANGE);
    }

    // 声明死信队列
    @Bean("dead_queue")
    public Queue deadQueue() {
        return new Queue("dead_queue");
    }

    // 声明死信队列与死信交换机的绑定关系
    @Bean("deadBinding")
    public Binding deadBinding(@Qualifier("dead_queue") Queue deadQueue,
                               @Qualifier(DEAD_EXCHANGE) DirectExchange deadExchange) {
        return BindingBuilder.bind(deadQueue).to(deadExchange).with("lisi");
    }

    // 声明普通交换机
    @Bean(NORMAL_EXCHANGE)
    public DirectExchange normalExchange() {
        return new DirectExchange(NORMAL_EXCHANGE);
    }

    // 声明普通队列
    @Bean("normal_queue")
    public Queue normalQueue() {
        Map<String, Object> args = new HashMap<>(3);
        //声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        //声明当前队列的死信路由 key
        args.put("x-dead-letter-routing-key", "lisi");
        // 设置队列长度
        //args.put("x-max-length", 6);
        return QueueBuilder.durable("normal_queue").withArguments(args).build();
    }

    // 声明普通队列与普通交换机的绑定关系
    @Bean("normalBinding")
    public Binding normalBinding(@Qualifier("normal_queue") Queue normalQueue,
                                 @Qualifier(NORMAL_EXCHANGE) DirectExchange normalExchange) {
        return BindingBuilder.bind(normalQueue).to(normalExchange).with("zhansan");
    }
}
