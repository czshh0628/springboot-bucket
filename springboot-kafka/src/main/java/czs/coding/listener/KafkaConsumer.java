package czs.coding.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 *
 * @author czs
 */
@Component
public class KafkaConsumer {

    // 指定要监听的 topic
    @KafkaListener(topics = "test")
    public void testTopic(String msg) {
        // 参数: 收到的 value
        System.out.println("收到的信息: " + msg);
    }
}
