package org.swp391.valuationdiamond.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class ApiOpenCongif {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info().title("Valuation Diamond API")
                        .license(new License().url("https://valuation.techtheword.id.vn"))
                        .version("1.0"))
                .servers(Collections.singletonList(new Server().url("https://valuation.techtheword.id.vn/")));
    }
}