package com.shier.mall.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * Demo class
 *
 * @author shierS
 * @date 2021/4/15
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {

    //配置Swagger的Docker的bean实例
    @Bean
    public Docket docket(Environment environment) {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()// 通过.select()方法，去配置扫描接口,RequestHandlerSelectors配置如何扫描接口
                .apis(RequestHandlerSelectors.basePackage("com.shier.mall.controller"))
                // 配置如何通过path过滤,即这里只扫描请求以/kuang开头的接口
                //.paths(PathSelectors.ant("/admin/**"))
                .build()
                .groupName("拾贰s");
    }

    //配置Swagger信息 apiInfo
    private ApiInfo apiInfo() {
        //作者信息  "联系人名字", "联系人访问链接", "联系人邮箱"
        Contact contact = new Contact("拾贰s", "https://www.yuque.com/shiers", "1173919359@qq.com");
        return new ApiInfo(
                "拾贰s的聚食集商城项目接口文档",  // 标题
                "阿巴阿巴阿巴~👻阿巴阿巴阿巴~👻阿巴阿巴阿巴~👻", // 描述
                "v1.2", // 版本
                "https://www.yuque.com/shiers", // 组织链接
                contact, // 联系人信息
                "Apache 2.0", // 许可
                "http://www.apache.org/licenses/LICENSE-2.0", // 许可连接
                new ArrayList() // 扩展
        );
    }
}
