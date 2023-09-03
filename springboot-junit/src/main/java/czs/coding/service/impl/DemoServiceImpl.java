package czs.coding.service.impl;

import czs.coding.service.DemoService;
import org.springframework.stereotype.Service;

/**
 * @author czs
 */
@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String trans(String name) {
        return "hello world " + name;
    }
}
