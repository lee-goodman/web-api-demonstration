package lg.webapidemo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@ConditionalOnWebApplication
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        // @formatter:off
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("lg.webapidemo"))
            .build()
            .apiInfo(new ApiInfo(
                "Web API Demonstration",
                "Demonstration of web APIs using a simple maze game",
                "",
                "",
                new Contact("Matthew Bretten & Lee Goodman", "https://github.com/lee-goodman/web-api-demonstration", ""),
                "",
                "",
                Collections.emptyList()));
        // @formatter:on
    }
}
