package insert;

import czs.coding.MybatisPlusApplication;
import czs.coding.entity.User;
import czs.coding.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = MybatisPlusApplication.class)
public class SimpleInsert {

    @Resource
    private UserMapper userMapper;

    /**
     * user的id会自动填充
     */
    @Test
    public void basicInsert() {
        User user = new User();
        user.setName("czs");
        user.setAge(18);
        user.setEmail("123@qq.com");
        userMapper.insert(user);
        System.out.println("id自动获取：" + user.getId());
    }

}
