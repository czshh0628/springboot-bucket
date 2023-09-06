package czs.coding.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import czs.coding.entity.User;
import czs.coding.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 删除条件选择器
 *
 * @author czs
 */
@RestController
public class DeleteWrapperController {

    @Resource
    private IUserService userService;

    /**
     * 删除email为空的用户
     * DELETE FROM t_user WHERE (email IS NULL)
     */
    @GetMapping("basicDeleteWrapper")
    public void basicDeleteWrapper() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("email");

        userService.remove(queryWrapper);
    }
}
