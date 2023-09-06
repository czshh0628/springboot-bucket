package czs.coding.controller;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 死信消息
 *
 * @author czs
 */
@RequestMapping("dead")
@RestController
public class DeadController {

    @Resource
    private RabbitTemplate rabbitTemplate;


    @GetMapping("product")
    public void product() {
        rabbitTemplate.convertAndSend("normal_exchange", "zhansan", "消息", new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                // 设置消息10秒过期
                message.getMessageProperties().setExpiration("10000");
                return message;
            }
        });
    }

    // 正常接受消息队列
    @RabbitListener(queues = {"normal_queue"})
    public void consumer(Message message, String content, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        System.out.println("正常队列接受消息：" + content);
        channel.basicNack(deliveryTag, false, false);
    }

    // 死信接受消息队列
    @RabbitListener(queues = {"dead_queue"})
    public void dead(String content) {
        System.out.println("死信队列接受消息：" + content);
    }
}
