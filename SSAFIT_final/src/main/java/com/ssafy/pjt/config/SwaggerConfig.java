package com.ssafy.pjt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
@RequestMapping("/ssafit")
public class SwaggerConfig {
   
     @Bean
     public OpenAPI springShopOpenAPI() {
         return new OpenAPI()
                 .info(new Info().title("%동%")
                 .description("%동%.")
                 .version("v0.0.1")
                 .license(new License().name("SSAFY").url("http://ssafy.com")));
     }
}
