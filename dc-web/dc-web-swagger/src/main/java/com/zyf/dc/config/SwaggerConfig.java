package com.zyf.dc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("itmc")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.evergrande.itmc.controller"))
                .paths(
                        or(
                                regex("/todo.*"),
                                regex("/todoDid.*"),
                                regex("/follow.*"),
                                regex("/message.*"),
                                regex("/approved.*")
                        )
                )
                .build()
                .apiInfo(itmcApiInfo());
    }

    private ApiInfo itmcApiInfo() {
        return new ApiInfoBuilder()
                .title("管理平台 API")//大标题
                .description("提供前端查看及测试 API.")//详细描述
                .version("1.0")//版本
                .termsOfServiceUrl("NO terms of service")
                .contact(new Contact("zengyufei", "http://localhost:8081", "312636208@qq.com"))//作者
                .build();

    }
}