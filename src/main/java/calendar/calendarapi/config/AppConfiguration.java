package calendar.calendarapi.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import calendar.calendarapi.instantiation.EventsInstantiator;
import calendar.calendarapi.instantiation.RecurrentEventsInstantiator;
import calendar.calendarapi.instantiation.SimpleEventsInstantiator;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class AppConfiguration {

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
    @Bean
	public Docket api() { 
	    return new Docket(DocumentationType.SWAGGER_2)  
	      .select()                                  
          .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
	      .paths(PathSelectors.any())                     
	      .build()
	      .apiInfo(apiInfo());
	}
    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
           .title("Calendar REST API")
           .description("This is a Calendar REST API created using Spring Boot")
           .termsOfServiceUrl("")
           .version("1.0.0")
           .build();
    }
    
    
    @Bean
    public EventsInstantiator eventsInstantiator() {
    	return new RecurrentEventsInstantiator(new SimpleEventsInstantiator(null));
    }
    
}
