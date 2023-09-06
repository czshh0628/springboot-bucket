package czs.coding.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import czs.coding.entity.User;
import czs.coding.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 查询条件选择器
 *
 * @author czs
 */
@RestController
public class QueryWrapperController {

    @Resource
    private IUserService userService;

    @GetMapping("basicQueryWrapper")
    public void basicQueryWrapper() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .like("username", "a")
                .between("age", 20, 30)
                .isNotNull("email")
                .orderByDesc("age")
                .orderByAsc("id");

        List<User> list = userService.list(queryWrapper);
        list.forEach(System.out::println);
    }

    /**
     * 查询id小于等于3的用户信息
     * SELECT id,username AS name,age,email,is_deleted FROM t_user WHERE (id IN (select id from t_user where id <= 3))
     * 子查询
     */
    @GetMapping("subQuery")
    public void subQuery() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("id", "select id from t_user where id <= 3");
        List<User> list = userService.list(queryWrapper);
        list.forEach(System.out::println);
    }

    /**
     * 组装查询
     * <p>
     * 定义的查询条件，有可能为null（用户未输入或未选择），则动态判断是否要拼接
     * <p>
     * SELECT id,username AS name,age,email,is_deleted FROM t_user WHERE (age >= ? AND age <= ?)
     */
    @GetMapping("conditionQuery")
    public void conditionQuery(String username, Integer ageBegin, Integer ageEnd) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        //StringUtils.isNotBlank()判断某字符串是否不为空且长度不为0且不由空白符(whitespace)构成
        queryWrapper
                .like(StringUtils.isNotBlank(username), "username", "a")
                .ge(ageBegin != null, "age", ageBegin)
                .le(ageEnd != null, "age", ageEnd);

        List<User> users = userService.list(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 可以有效避免使用字符串表示字段，防止运行时错误
     */
    @GetMapping("lambdaQuery")
    public void lambdaQuery(String username, Integer ageBegin, Integer ageEnd) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper
                .like(StringUtils.isNotBlank(username), User::getName, username)
                .ge(ageBegin != null, User::getAge, ageBegin)
                .le(ageEnd != null, User::getAge, ageEnd);

        List<User> users = userService.list(queryWrapper);
        users.forEach(System.out::println);
    }
}
