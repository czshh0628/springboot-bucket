package czs.coding.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import czs.coding.entity.User;
import czs.coding.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 更新条件选择器
 *
 * @author czs
 */
@RestController
public class UpdateWrapperController {

    @Resource
    private IUserService userService;

    /**
     * 将用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息修改
     * UPDATE t_user SET age=?, email=? WHERE (username LIKE ? AND (age > ? OR email IS NULL))
     */
    @GetMapping("basicUpdateWrapper")
    public void basicUpdateWrapper() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .like("username", "a")
                .and(i -> i.gt("age", 20).or().isNull("email"));
        User user = new User();
        user.setAge(18);
        user.setEmail("user@atguigu.com");
        userService.update(user, queryWrapper);
    }

    /**
     * 将（年龄大于20或邮箱为null）并且用户名中包含有a的用户信息修改
     * <p>
     * UPDATE t_user SET age=?,email=? WHERE (username LIKE ? AND (age > ? OR email IS NULL))
     */
    @GetMapping("setUpdateWrapper")
    public void setUpdateWrapper() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();

        // lambda表达式内的逻辑优先运算
        updateWrapper
                .set("age", 18)
                .set("email", "user@atguigu.com")
                .like("username", "a")
                .and(i -> i.gt("age", 20).or().isNull("email"));

        // entity内的后运算
        User user = new User();
        user.setName("张三");

        userService.update(user, updateWrapper);
    }

    /**
     * 可以不写字段名，用实体名，防止写错
     */
    @GetMapping("lambdaUpdateWrapper")
    public void lambdaUpdateWrapper() {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();

        // lambda表达式内的逻辑优先运算
        updateWrapper
                .set(User::getAge, 18)
                .set(User::getEmail, "user@atguigu.com")
                .like(User::getName, "a")
                .and(i -> i.lt(User::getAge, 24).or().isNull(User::getEmail));

        // entity内的后运算
        User user = new User();
        user.setName("张三");

        userService.update(user, updateWrapper);
    }
}
