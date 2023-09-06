package czs.coding.controller;

import czs.coding.entity.User;
import czs.coding.enums.SexEnum;
import czs.coding.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 添加条件选择器
 *
 * @author czs
 */
@RestController
public class InsertWrapperController {

    @Resource
    private IUserService userService;

    /**
     * 通用枚举
     * <p>
     * 设置性别信息为枚举项，会将@EnumValue注解所标识的属性值存储到数据库
     * <p>
     * INSERT INTO user ( name, age, sex ) VALUES ( ?, ?, ? )  Parameters: Enum(String), 20(Integer), 1(Integer)
     */
    @GetMapping("basicInsert")
    public void basicInsert() {
        User user = new User();
        user.setName("Enum");
        user.setAge(20);
        //设置性别信息为枚举项，会将@EnumValue注解所标识的属性值存储到数据库
        user.setSex(SexEnum.FEMALE);

        userService.save(user);
    }
}
