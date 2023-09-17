package com.postype.sns.global.configuration;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	@Bean
	public Docket testDocket() {
		return new Docket(DocumentationType.OAS_30)
			.useDefaultResponseMessages(false)
			.securityContexts(List.of(this.securityContext())) // SecurityContext 설정
			.securitySchemes(List.of(this.apiKey())) // ApiKey 설정
			.apiInfo(this.getApiInfo())
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.postype.sns.application.controller"))
			.paths(PathSelectors.ant("/api/**"))
			.build();
	}

	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder()
			.title("API")
			.description("postype API")
			.contact(new Contact("yooyouny", "https://yooyouny.tistory.com/", "jhjin2u@gmail.com"))
			.version("1.0")
			.build();
	}

	// JWT SecurityContext 구성
	private SecurityContext securityContext() {
		return SecurityContext.builder()
			.securityReferences(defaultAuth())
			.build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return List.of(new SecurityReference("Authorization", authorizationScopes));
	}

	// ApiKey 정의
	private ApiKey apiKey() {
		return new ApiKey("Authorization", "Authorization", "header");
	}
}
