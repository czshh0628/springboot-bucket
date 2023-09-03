package czs.coding.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author czs
 */
@Api(tags = "用户接口")
@RestController
public class TestController {

    @GetMapping("/user/{id}")
    @ApiOperation(value = "根据 ID 获取用户信息")
    public void getUserById(@ApiParam(value = "用户 ID", required = true) @PathVariable Long id) {
        System.out.println("根据ID获取用户信息");
    }
}
