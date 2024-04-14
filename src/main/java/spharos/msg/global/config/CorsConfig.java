package spharos.msg.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedMethods(CorsConfiguration.ALL)
                .allowedHeaders(CorsConfiguration.ALL)
//                .allowedOriginPatterns(CorsConfiguration.ALL)

                //add
                .allowedOrigins("http://localhost:3000", "https://ssgcom-app.vercel.app", "https://sssg.shop")
                .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
                .maxAge(3600L)
                //__

                .exposedHeaders(CorsConfiguration.ALL)
                .allowCredentials(true);
    }
}