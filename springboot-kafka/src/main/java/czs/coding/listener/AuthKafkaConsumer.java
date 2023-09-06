package czs.coding.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 认证消费者
 *
 * @author czs
 */
@Component
public class AuthKafkaConsumer {
    static {
        System.setProperty("java.security.krb5.conf", "krb5.conf");
    }

    @KafkaListener(topics = "auth_topic", groupId = "auth_group")
    public void authTopic(String msg) {
        System.out.println("接收到认证kafka发送来的消息: " + msg);
    }
}
