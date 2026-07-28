package com.prueba.examen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
	@Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Prueba de Pagos")
                .version("0.0.1")
                .description("Servicio para dar de alta, consultar y actualizar el estatus de pagos y agregar notificaciones en RabbitMQ")
                .contact(new Contact()
                    .name("Héctor Daniel García Sánchez")
                    .email("garciasanchezhectordaniel@gmail.com")));
    }
}
