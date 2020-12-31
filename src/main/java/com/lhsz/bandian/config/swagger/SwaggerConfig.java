package com.lhsz.bandian.config.swagger;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.lhsz.bandian.config.properties.BandianProperties;
import org.springframework.beans.factory.annotation.Value;
import springfox.documentation.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.RequestHandler;
import com.google.common.base.Predicate;
import com.google.common.base.Function;
import com.google.common.base.Optional;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {
    // 定义分隔符
    private static final String splitor = ";";
    /** 是否开启swagger */
    @Value("${swagger.enabled}")
    private boolean enabled;

    /** 设置请求的统一前缀 */
    @Value("${swagger.pathMapping}")
    private String pathMapping;
//    @Bean
//    public Docket createRestApi(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.none())
//                .paths(PathSelectors.ant("/jt/**"))
//                .build();
//    }
//
//    private ApiInfo apiInfo(){
//        return new ApiInfoBuilder()
//                .title("SpringBoot API Doc")
//                .description("This is a restful api document of Spring Boot.")
//                .version("1.0")
//                .build();
//    }
    @Bean
    public Docket web_api_sys(){
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enabled)
                .apiInfo(apiInfoSys())
                .select()
                .apis(basePackage("com.lhsz.bandian.sys.controller;com.lhsz.bandian.controller"))
                .paths((String a) ->
                        !a.equals("/sys/claim"))
                .build()
                .securitySchemes(unifiedAuth())
                .groupName("系统模块")
                .securityContexts(securityContexts())
                .pathMapping(pathMapping);
    }
    @Bean
    public Docket web_api_jt() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enabled)
                .apiInfo(apiInfozcps())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/jt/**"))
                .build()
                .securitySchemes(unifiedAuth())
                .groupName("职称评审")
                .pathMapping(pathMapping);
    }
    @Bean
    public Docket web_api_portal() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enabled)
                .apiInfo(apiInfoportal())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/portal/**"))
                .build()
                .securitySchemes(unifiedAuth())
                .groupName("职称评审前端")
                .pathMapping(pathMapping);
    }
    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage)     {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(splitor)) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }
    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }
    /**
     * 对每个API都添加header字段
     *  new Docket().globalOperationParameters( getParameters())
     * @return
     */
    private List<Parameter> getParameters() {
        // 添加请求参数，我们这里把token作为请求头部参数传入后端
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameterBuilder.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        parameters.add(parameterBuilder.build());
        return parameters;
    }
    /**
     *全站统一header设置
     * @return
     */
    private static List<ApiKey> unifiedAuth() {
        List<ApiKey> apiKeyList = new ArrayList<ApiKey>();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
        return apiKeyList;
    }
    /**
     * 安全上下文
     */
    private List<SecurityContext> securityContexts()
    {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build());
        return securityContexts;
    }
    /**
     * 默认的安全上引用
     */
    private List<SecurityReference> defaultAuth()
    {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }
    private ApiInfo apiInfoSys(){
        return new ApiInfoBuilder()
                .title("斑点 API Doc")
                .description("系统管理 restful api")
                //这里可以是项目地址
                .termsOfServiceUrl("")
                .version("1.0")
                .contact(new Contact(BandianProperties.name,BandianProperties.url,BandianProperties.email))
                .build();
    }
    private ApiInfo apiInfozcps(){
        return new ApiInfoBuilder()
                .title("斑点 API Doc")
                .description("支持评审 restful api")
                //这里可以是项目地址
                .termsOfServiceUrl("")
                .version("1.0")
                .contact(new Contact(BandianProperties.name,BandianProperties.url,BandianProperties.email))
                .build();
    }
    private ApiInfo apiInfoportal(){
        return new ApiInfoBuilder()
                .title("斑点 API Doc")
                .description("支持评审前端 restful api")
                //这里可以是项目地址
                .termsOfServiceUrl("")
                .version("1.0")
                .contact(new Contact(BandianProperties.name,BandianProperties.url,BandianProperties.email))
                .build();
    }
}
