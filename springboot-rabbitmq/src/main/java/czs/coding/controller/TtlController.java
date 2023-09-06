package czs.coding.controller;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 消息的过期时间设置
 *
 * @author czs
 */
@RequestMapping("ttl")
@RestController
public class TtlController {

    @Resource
    private RabbitTemplate rabbitTemplate;

    // 发送延时消息
    @GetMapping("sendMsg/{msg}")
    public void sendMsg(@PathVariable("msg") String msg) {

        rabbitTemplate.convertAndSend("X", "XA", "消息为10秒" + msg);
        rabbitTemplate.convertAndSend("X", "XB", "消息为40秒" + msg);


    }

    // 发送指定延时时间的消息
    @GetMapping("sendTtlMsg/{ttl}/{msg}")
    public void sendTtlMsg(@PathVariable("ttl") String ttl, @PathVariable("msg") String msg) {
        rabbitTemplate.convertAndSend("X", "XC", msg, message -> {
            // 发送消息时候的延时时长
            message.getMessageProperties().setExpiration(ttl);
            return message;
        });

    }

    // 监听延时队列
    @RabbitListener(queues = {"QD"})
    public void receiveD(Message message, String content, Channel channel) {
        System.out.println("接受消息： " + content);
    }

}
