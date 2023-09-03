package czs.coding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author czs
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {

    @Bean
    public Docket desertsApi(){
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("czs.coding.controller")) //这里需要写你需要扫描的包路径
                .paths(PathSelectors.any())
                .build()
                .groupName("default")
                .enable(true);
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("微服务 API说明文档")
                .description("微服务 API说明文档")
                .contact(new Contact("SpringBoot", "https://gitee.com/czshh0628/", "760620329@qq.com"))
                .termsOfServiceUrl("https://gitee.com/czshh0628/")
                .version("1.0")
                .build();
    }
}
