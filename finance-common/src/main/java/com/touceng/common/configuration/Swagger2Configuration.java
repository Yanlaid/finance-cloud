package com.touceng.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.touceng.common.constant.TCConfigConstant;

import org.springframework.core.annotation.Order;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: Swagger2配置文件
 * @createTime 2018年7月3日 上午9:33:39
 * @copyright: 上海投嶒网络技术有限公司
 */
@Order(value = 10)
@Configuration
@EnableSwagger2
public class Swagger2Configuration {


    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.touceng")).paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder().title("服务端SwaggerAPI").description(TCConfigConstant.project_name)
                .termsOfServiceUrl("http://tengceng.com/")
                .contact(new Contact("技术部-Wu,Hua-Zheng", "http://tengceng.com/", "Wu,Hua-Zheng")).version("1.0")
                .build();
    }

}
