package czs.coding.controller;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author czs
 */
@RestController
public class RedisController {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("put")
    public void put() {
        redisTemplate.opsForValue().set("name", "czs");
    }

    @GetMapping("get")
    public String get() {
        return redisTemplate.opsForValue().get("name");
    }

    @Cacheable(value = "addRedis", keyGenerator = "keyGenerator")
    @GetMapping("addRedis")
    public List<String> addRedis() {
        List<String> list = new ArrayList<>();
        list.add("张三");
        list.add("李四");
        list.add("王五");
        list.add("czs");
        return list;
    }

    @CachePut(value = "updateRedis", keyGenerator = "keyGenerator")
    @GetMapping("updateRedis")
    public List<String> updateRedis() {
        List<String> list = new ArrayList<>();
        list.add("张三");
        list.add("李四");
        list.add("czs");
        return list;
    }

    @CacheEvict(value = "addRedis", allEntries = true)
    @GetMapping("deleteRedis")
    public List<String> deleteRedis() {
        List<String> list = new ArrayList<>();
        list.add("张三");
        return list;
    }

}
