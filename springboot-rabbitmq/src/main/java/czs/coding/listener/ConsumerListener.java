package czs.coding.listener;

/**
 * 客户端测试
 *
 * @author czs
 */

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 消息监听器
 *
 * @author czs
 */
@Component
public class ConsumerListener {
    /**
     * 接收消息
     *
     * @param message：原生消息详细信息
     * @param content：发送类型是什么就用什么类型接收
     * @param channel：当前传输数据的通道
     */
    @RabbitListener(queues = {"hello-queue"})
    public void receiveMessage(Message message, String content, Channel channel) {
        // byte[] body = message.getBody();
        // MessageProperties messageProperties = message.getMessageProperties();
        // 通道内按顺序自增
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        System.out.println("接收到消息...内容..." + content + "deliveryTag：" + deliveryTag);

        try {
            // 确认消息接收成功，非批量签收模式
            // long deliveryTag, boolean multiple （当前消息的标签，是否批量签收）
            channel.basicAck(deliveryTag, false);
            // 消息接收成功，但是拒绝签收消息
            // long deliveryTag, boolean multiple, boolean requeue （当前消息的标签，是否批量签收，是否重新入队（false丢掉消息，true将消息重新入队））
            // channel.basicNack(deliveryTag, false, true);
        } catch (IOException e) {
            // 网络中断
        }
    }
}
