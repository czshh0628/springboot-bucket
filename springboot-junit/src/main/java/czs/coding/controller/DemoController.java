package czs.coding.controller;

import czs.coding.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author czs
 */
@RestController
public class DemoController {

    @Resource
    private DemoService demoService;

    @GetMapping("trans")
    public String trans(String name) {
        return demoService.trans(name);
    }
}
