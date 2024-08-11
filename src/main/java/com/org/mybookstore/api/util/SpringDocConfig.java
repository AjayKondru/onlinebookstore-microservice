package com.org.mybookstore.api.util;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SpringDocConfig {

	@Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public io.swagger.v3.oas.models.OpenAPI customOpenAPI() {
        return new io.swagger.v3.oas.models.OpenAPI()
                .info(new Info().title("Bookstore API")
                .version("1.0")
                .description("Bookstore API documentation")
                .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
