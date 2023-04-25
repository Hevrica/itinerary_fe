package itinerary.itinerary_fe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	private String title = "여행 일정표 API";
	private String description = "여행 일정표 API 서비스 입니다.";
	private String version = "0.1.0";
	private String termsOfServiceUrl = "none";
	private String name = "Hevrica";
	private String url = "";
	private String email = "jungtb0606@gmail.com";
	private String license = "License";
	private String licenseUrl = "LicenseUrl";
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				//.apis(RequestHandlerSelectors.basePackage("itinerary.itinerary_fe"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title(title)
				.description(description)
				.version(version)
				.termsOfServiceUrl(termsOfServiceUrl)
				.contact(new Contact(name, url, email))
				.license(license)
				.licenseUrl(licenseUrl)
				.build();
	}
}