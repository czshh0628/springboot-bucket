package delete;

import czs.coding.MybatisPlusApplication;
import czs.coding.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = MybatisPlusApplication.class)
public class SimpleDelete {

    @Resource
    private UserMapper userMapper;

    @Test
    public void basicDelete() {
        int result = userMapper.deleteById(1);
        System.out.println("受影响行数：" + result);
    }

    /**
     * 通过多个id批量删除
     * DELETE FROM user WHERE id IN ( ? , ? , ? )
     */
    @Test
    public void basicBatchDelete() {
        List<Long> idList = Arrays.asList(1L, 2L, 3L);
        int result = userMapper.deleteBatchIds(idList);
        System.out.println("受影响行数：" + result);
    }

    /**
     * 根据map集合中所设置的条件删除记录
     * DELETE FROM user WHERE name = ? AND age = ?
     */
    @Test
    public void deleteByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("age", 18);
        map.put("name", "czs");
        int result = userMapper.deleteByMap(map);
        System.out.println("受影响行数：" + result);
    }
}
