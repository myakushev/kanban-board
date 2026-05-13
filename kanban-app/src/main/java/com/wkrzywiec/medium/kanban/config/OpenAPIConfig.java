package com.wkrzywiec.medium.kanban.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Kanban REST API")
                        .version("v1")
                        .description("This is a REST API of Kanban REST API, where you can get/add/remove/modify Kanban board and its task.")
                        .contact(new Contact()
                                .name("Wojtek Krzywiec")
                                .url("www.github.com/wkrzywiec")
                                .email(""))
                        .license(new License()
                                .name("License of API")
                                .url("API license URL")));
    }
}