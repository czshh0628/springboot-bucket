package query;

import czs.coding.MybatisPlusApplication;
import czs.coding.entity.User;
import czs.coding.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = MybatisPlusApplication.class)
public class SimpleQuery {

    @Resource
    private UserMapper userMapper;

    @Test
    public void listQuery() {
        userMapper.selectList(null).forEach(System.out::println);
    }

    /**
     * 根据id查询用户信息
     * SELECT id,name,age,email FROM user WHERE id=?
     */
    @Test
    public void queryById() {
        User user = userMapper.selectById(4L);
        System.out.println(user);
    }

    /**
     * 根据多个id查询多个用户信息
     * SELECT id,name,age,email FROM user WHERE id IN ( ? , ? )
     */
    @Test
    public void queryByIds() {
        List<Long> idList = Arrays.asList(4L, 5L);
        List<User> list = userMapper.selectBatchIds(idList);
        list.forEach(System.out::println);
    }

    /**
     * 通过map条件查询用户信息
     * SELECT id,name,age,email FROM user WHERE name = ? AND age = ?
     */
    @Test
    public void queryByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("age", 22);
        map.put("name", "admin");
        List<User> list = userMapper.selectByMap(map);
        list.forEach(System.out::println);
    }
}
