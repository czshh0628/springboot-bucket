package czs.coding.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 性别通用枚举
 *
 * @author czs
 */
public enum SexEnum {

    MALE(1,"男"),

    FEMALE(2,"女");

    @EnumValue
    private final Integer sex;
    private final String sexName;

    SexEnum(Integer sex, String sexName) {
        this.sex = sex;
        this.sexName = sexName;
    }
}
