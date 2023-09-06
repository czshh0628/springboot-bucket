package update;

import czs.coding.MybatisPlusApplication;
import czs.coding.entity.User;
import czs.coding.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = MybatisPlusApplication.class)
public class SimpleUpdate {

    @Resource
    private UserMapper userMapper;

    /**
     * UPDATE user SET name=?, age=? WHERE id=?
     */
    @Test
    public void basicUpdate() {
        User user = new User();
        user.setId(1L);
        user.setName("czs");
        int result = userMapper.updateById(user);
        System.out.println("受影响行数：" + result);
    }

}
