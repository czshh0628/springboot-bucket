package czs.coding.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DemoServiceTest {

    @Resource
    private DemoService demoService;

    @Test
    void trans() {
        String czs = demoService.trans("czs");
        System.out.println(czs);
    }
}