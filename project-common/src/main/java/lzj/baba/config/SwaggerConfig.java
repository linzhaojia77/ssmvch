package lzj.baba.config;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: TODO
 * @author: lzj
 * @date: 2021年07月29日 11:20
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {
    @Value("${jwt.header}")
    private String tokenHeader;
    @Value("${swagger.enabled}")
    private Boolean enabled;
    @Bean
    public Docket createDocket(){
        return new Docket(DocumentationType.SWAGGER_2)//设置版本
        .enable(enabled)//设置swagger开关
        .pathMapping("/")//设置生成document文档的路径（http://localhost:8080/swagger-ui.html）
        .apiInfo(apiInfo())//配置一些基础信息
        .select()
        .paths(Predicates.not(PathSelectors.regex("/error.*"))) //不监控错误路径
        .paths(PathSelectors.any())//其余路径都监控
        .build()//建成Docket;
        .securityContexts(Arrays.asList(securityContexts()))
        .securitySchemes(Arrays.asList(securitySchemes()));

    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .description("一个简单且易上手的 Spring boot 后台管理框架")
                .title("Mylastproject项目的 接口文档")
                .version("1.0")
                .build();
    }
    private SecurityScheme securitySchemes() {
        //设置请求头信息
         return new ApiKey(tokenHeader, tokenHeader, "header");
    }

//    private List<SecurityContext> securityContexts() {
//        //设置需要登录认证的路径
//        List<SecurityContext> securityContexts = new ArrayList<>();
//        // ^(?!auth).*$ 表示所有包含auth的接口不需要使用securitySchemes即不需要带token
//        // ^标识开始  ()里是一子表达式  ?!/auth表示匹配不是/auth的位置，匹配上则添加请求头，注意路径已/开头  .表示任意字符  *表示前面的字符匹配多次 $标识结束
//        securityContexts.add(getContextByPath());
//        return securityContexts;
//    }

    private SecurityContext securityContexts() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("^(?!/auth).*$"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> securityReferences = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        securityReferences.add(new SecurityReference(tokenHeader, authorizationScopes));
        return securityReferences;
    }
}
