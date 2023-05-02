package itinerary.itinerary_fe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
	info = @Info(
		title = "Swagger",
		description = "Swagger Description",
		version = "0.1",
		contact = @Contact(
				name = "Hevrica",
				email = "none",
				url = "none"),
		termsOfService = "",
		license = @License(
			name = "none",
			url = "none")
	)
)
@Configuration
public class OpenApiConfig implements WebMvcConfigurer {
	
}