package czs.coding.controller;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * 发送消息
 *
 * @author czs
 */
@RequestMapping("product")
@RestController
public class ProductController {

    @Resource
    private AmqpAdmin amqpAdmin;

    @Resource
    private RabbitTemplate rabbitTemplate;

    // 创建交换机
    @GetMapping("createExchange/{exchangeName}")
    public void createExchange(@PathVariable("exchangeName") String exchangeName) {
        /**
         * 生成一个交换机
         * 1. name：交换机名称
         * 2. durable：是否持久化
         * 3. autoDelete：是否自动删除
         * 4. 其他参数
         */
        DirectExchange directExchange = new DirectExchange(exchangeName, true, false, null);
        amqpAdmin.declareExchange(directExchange);
    }

    // 创建队列
    @GetMapping("createQueue/{queueName}")
    public void createQueue(@PathVariable("queueName") String queueName) {
        /**
         * 生成一个队列
         * 1. name：队列名称
         * 2. durable：是否持久化
         * 3. exclusive：是否只提供一个消费者进行消费，true可以多个消费者消费
         * 4. autoDelete：是否自动删除
         * 5. 其他参数
         */
        Queue queue = new Queue(queueName, true, false, false, null);
        amqpAdmin.declareQueue(queue);
    }

    // 绑定交换机和队列
    @GetMapping("binding/{exchangeName}/{queueName}")
    public void binding(@PathVariable("exchangeName") String exchangeName, @PathVariable("queueName") String queueName) {
        /**
         * 1. 绑定信息
         * 2. 绑定信息的类型
         * 3. 目标绑定位置
         * 4. 路由Key
         * 5. 其他参数
         */
        Binding binding = new Binding(queueName, Binding.DestinationType.QUEUE, exchangeName, "hello", null);
        amqpAdmin.declareBinding(binding);
    }

    // 发送消息
    @GetMapping("sendMsg/{exchangeName}/{msg}")
    public void sendMsg(@PathVariable("exchangeName") String exchangeName, @PathVariable("msg") String msg) {
        /**
         * 1. exchange：发送到哪个交换机
         * 2. routingKey：路由的Key是哪个
         * 3. message：消息体
         * 4. CorrelationData：消息的唯一ID
         */
        rabbitTemplate.convertAndSend(exchangeName, "hello", msg, new CorrelationData(UUID.randomUUID().toString()));
    }

    // 发送消息
    @GetMapping("send/{msg}")
    public void send(@PathVariable("msg") String msg) {
        /**
         * 1. exchange：发送到哪个交换机
         * 2. routingKey：路由的Key是哪个
         * 3. message：消息体
         * 4. CorrelationData：消息的唯一ID
         */
        rabbitTemplate.convertAndSend("", "hello-queue", msg, new CorrelationData(UUID.randomUUID().toString()));
    }
}
