package czs.coding.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 *
 * @author czs
 */
@Configuration
@MapperScan("czs.coding.mapper")
public class MybatisPlusConfig {
}
