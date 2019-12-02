package com.iqianjin.test.teststage.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerBootstrapConfiguration {

    /**
     * https://github.com/xiaoymin/Swagger-Bootstrap-UI/blob/master/README_zh.md
     * http://${host}:${port}/doc.html
     * @return
     */
    @Bean
    public Docket creatRestApi(){
        return new Docket((DocumentationType.SWAGGER_2))
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.iqianjin.test.teststage.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("新测试平台接口文档")
                .description("swagger-bootstrap-ui")
                .version("1.0")
                .build();
    }
}
