package czs.coding.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 发送消息
 *
 * @author czs
 */
@RestController
public class SendController {

    @Resource
    KafkaTemplate<String, String> kafkaTemplate;


    @GetMapping("sendMsg")
    public String sendMsg(String msg) {
        kafkaTemplate.send("test", msg);
        return msg;
    }
}
