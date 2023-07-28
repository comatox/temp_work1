package com.skshieldus.esecurity;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class EsecurityApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(EsecurityApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .components(new Components().addSecuritySchemes("basicScheme", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
            .info(new Info().title("esecurity-api").version("1.0.0").license(new License().name("Apache 2.0").url("http://springdoc.org")))
            .path("/login/process", new PathItem()
                .post(new Operation().tags(List.of("Authentication")).summary("Login").description("Login with the given credentials.").operationId("login")
                    .parameters(List.of(
                        new Parameter().name("id").in("query").required(true).schema(new Schema().type("string")),
                        new Parameter().name("password").in("query").required(true).schema(new Schema().type("string"))
                    )).responses(new ApiResponses().addApiResponse("200", new ApiResponse().description("OK")))
                )
            );
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
            }
        };
    }

}
