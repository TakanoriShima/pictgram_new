import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class WebMvcConfiguration {
	 @Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                // Enabling CORS for the whole application
	                // https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-cors-global-java
	                registry.addMapping("/**");
	            }
	        };
	    }
}
