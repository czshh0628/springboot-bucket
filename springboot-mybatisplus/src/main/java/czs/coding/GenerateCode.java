package czs.coding;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;

/**
 * 代码生成器
 *
 * @author czs
 */
public class GenerateCode {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/realtime?useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8", "root", "root")
                .globalConfig(builder -> {
                    builder.author("czs") // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("E:\\rebuild\\springboot-bucket\\springboot-mybatisplus\\src\\main\\java"); // 指定输出目录
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    builder.parent("czs") // 设置父包名
                            .moduleName("coding") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "E:\\rebuild\\springboot-bucket\\springboot-mybatisplus\\src\\main\\java\\czs\\coding\\mapper\\xml")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder
                            .addInclude("user_cdc")
                            // .addInclude("users_cdc") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
