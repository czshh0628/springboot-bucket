package czs.coding.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 认证kafka集群发送消息
 *
 * @author czs
 */
@RestController
public class AuthSendController {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("saslSend")
    public void saslSend(String msg) {
        System.setProperty("java.security.krb5.conf", "krb5.conf");

        kafkaTemplate.send("sasl_auth_topic", msg);
    }

    @GetMapping("kdcSend")
    public void kdcSend(String msg) {
        System.setProperty("java.security.krb5.conf", "krb5.conf");

        kafkaTemplate.send("kdc_auth_topic", msg);
    }
}
