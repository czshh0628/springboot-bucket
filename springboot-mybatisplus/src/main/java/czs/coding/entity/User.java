package czs.coding.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import czs.coding.enums.SexEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author czs
 * @since 2023-09-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 年龄
     */
    private SexEnum sex;

    /**
     * 邮箱
     */
    private String email;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer version;

    /**
     * 0 在用
     * 1 删除
     */
    @TableLogic(value = "0", delval = "1")
    private Integer deleted;
}
