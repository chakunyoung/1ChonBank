package com.woowahanbank.backend.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration  // Spring Boot Configuration 클래스임을 나타냄
@EnableSwagger2 // Swagger 2를 활성화
public class SwaggerConfig {

    private static final String API_NAME = "Woowahan Bank API";        // API 문서의 이름
    private static final String API_VERSION = "0.0.1";         // API 문서의 버전
    private static final String API_DESCRIPTION = "Woowahan Bank API 명세서";  // API 문서의 설명

    @Bean
    public Docket api() {
        // 전역 헤더 파라미터 (여기서는 Authorization 헤더) 설정을 위한 객체 생성
        Parameter parameterBuilder = new ParameterBuilder()
                .name(HttpHeaders.AUTHORIZATION)
                .description("Access Token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();

        // 전역 파라미터 리스트 초기화
        List<Parameter> globalParameters = new ArrayList<>();
        globalParameters.add(parameterBuilder); // 전역 파라미터 리스트에 방금 만든 헤더 파라미터 추가

        // Swagger 설정을 위한 Docket Bean 생성 및 반환
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(globalParameters) // 전역 파라미터 설정
                .apiInfo(apiInfo())  // API 정보 설정
                .select()  // API 문서에 표시될 API를 선택하기 위한 ApiSelectorBuilder 반환
                .apis(RequestHandlerSelectors.basePackage("com.woowahanbank.banking")) // 문서에 표시될 API를 해당 패키지 내에서 선택
                .paths(PathSelectors.any())  // 모든 URL 경로를 문서에 표시
                .build();  // 설정 적용
    }

    // API 정보 반환. 이 정보는 Swagger UI 페이지의 상단에 표시됨
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(API_NAME)
                .version(API_VERSION)
                .description(API_DESCRIPTION)
                .build();
    }
}
