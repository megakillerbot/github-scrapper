package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.SpringVersion;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

    @Value("${APP_URL}")
    private String appURL;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller")).build()
                .apiInfo(metaData()).protocols(protocols())
                .host(appURL);
    }

    private Set<String> protocols() {
        Set<String> protocols = new HashSet<>(1);
        protocols.add("http");
        protocols.add("https");
        return protocols;
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder().title("Github Scrapper API")
                .description("Applications responsible to manager user information on Telemetry Users." +
                        "\n Java version: "+System.getProperty("java.version") +
                        "\n Spring version: "+ SpringVersion.getVersion())
                .version("v1")
                .license("Copyrights 2021 - Jonathan David Freire e Silva")
                .licenseUrl("http://www.jonathandavid.tech").build();
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
