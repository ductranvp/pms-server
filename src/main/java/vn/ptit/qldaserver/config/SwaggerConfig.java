package vn.ptit.qldaserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import vn.ptit.qldaserver.security.JwtTokenProvider;
import vn.ptit.qldaserver.util.CommonConstants;

import static java.util.Collections.singletonList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Autowired
    JwtTokenProvider tokenProvider;

    @Bean
    public Docket apiDocket() {
        /* Token of admin */
        final String swaggerToken = tokenProvider.generateTokenForSwagger("1");
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(singletonList(
                        new ParameterBuilder()
                                .name("Authorization")
                                .modelRef(new ModelRef("string"))
                                .parameterType("header")
                                .required(false)
                                .hidden(true)
                                .allowEmptyValue(true)
                                .defaultValue("Bearer " + swaggerToken)
                                .build()
                        )
                )
                .select()
                .apis(RequestHandlerSelectors.basePackage(CommonConstants.APP_PACKAGE))
                .paths(PathSelectors.any())

                .build();

        return docket;

    }
}
