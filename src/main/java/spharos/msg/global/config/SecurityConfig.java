package spharos.msg.global.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import spharos.msg.global.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtTokenProvider;
    private final AuthenticationProvider authenticationProvider;

    //cors
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            var cors = new org.springframework.web.cors.CorsConfiguration();
            cors.setAllowedOriginPatterns(List.of("*"));
            cors.addAllowedOrigin("http://localhost:3000");
            cors.addAllowedOrigin("https://ssgcom-app.vercel.app/");
            cors.addAllowedHeader("*");
            cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            cors.setAllowedHeaders(List.of("*"));
            cors.setAllowCredentials(true);
            return cors;
        };
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(
                        authorizeHttpRequests -> authorizeHttpRequests
                                // swagger
                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/swagger-resources/**",
                                        "/api-docs/**").permitAll()
                                .requestMatchers("/error").permitAll()

                                //Auth
                                .requestMatchers("/api/v1/auth/check-duplicate-id").permitAll()
                                .requestMatchers("/api/v1/auth/find-id/**").permitAll()
                                .requestMatchers("/api/v1/auth/login").permitAll()
                                .requestMatchers("/api/v1/auth/signup").permitAll()

                                //Admin에서 회원 탈퇴 기능 사용하기 위해 일시적 허용
                                //todo : Admin 회원 인증 가능 하면 삭제 필요.
                                .requestMatchers("/api/v1/auth/secession/**").permitAll()

                                //OAuth (permitAll)
                                .requestMatchers("/api/v1/oauth/**").permitAll()

                                //User (permitALl)
                                .requestMatchers("/api/v1/users/**").permitAll()

                                //Address N/A
                                //비회원 주소 없음

                                //Product
                                .requestMatchers("/api/v1/product/**").permitAll()
                                .requestMatchers("/api/v1/products").permitAll()
                                .requestMatchers("/api/v1/random").permitAll()
                                .requestMatchers("/api/v1/ranking").permitAll()
                                .requestMatchers("/api/v1/ranking11").permitAll()

                                //Order N/A
                                //비회원 주문 없음

                                //Cart N/A
                                //비회원 장바구니 없음

                                //Review
                                .requestMatchers("/api/v1/product-review/*/reviews").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/product-review/**").permitAll()

                                //Option (permitAll)
                                .requestMatchers("/api/v1/option/**").permitAll()

                                //Like N/A
                                //비회원 좋아요 없음

                                //Coupon
                                .requestMatchers(HttpMethod.GET, "/api/v1/coupon").permitAll()

                                //Category (permitAll)
                                .requestMatchers("/api/v1/category").permitAll()
                                .requestMatchers("/api/v1/category-child").permitAll()
                                .requestMatchers("/api/v1/category-product").permitAll()

                                //Bundle (permitAll)
                                .requestMatchers("/api/v1/bundles").permitAll()
                                .requestMatchers("/api/v1/bundles/**").permitAll()

                                //Brand (permitAll)
                                .requestMatchers("/api/v1/brand").permitAll()
                                .requestMatchers("/api/v1/brand/**").permitAll()

                                //Search
                                .requestMatchers("/api/v1/search").permitAll()
                                .requestMatchers("/api/v1/search-list").permitAll()

                                //Admin (permit All)
                                //현재 Admin 인증 진행 하지 않음
                                //todo : Admin 회원 인증 가능 하면 삭제 필요.
                                .requestMatchers("/api/v1/admin/**").permitAll()








                                //V2 동일하게 처리
                                //Auth
                                .requestMatchers("/api/v2/auth/check-duplicate-id").permitAll()
                                .requestMatchers("/api/v2/auth/find-id/**").permitAll()
                                .requestMatchers("/api/v2/auth/login").permitAll()
                                .requestMatchers("/api/v2/auth/signup").permitAll()

                                //Admin에서 회원 탈퇴 기능 사용하기 위해 일시적 허용
                                //todo : Admin 회원 인증 가능 하면 삭제 필요.
                                .requestMatchers("/api/v2/auth/secession/**").permitAll()

                                //OAuth (permitAll)
                                .requestMatchers("/api/v2/oauth/**").permitAll()

                                //User (permitALl)
                                .requestMatchers("/api/v2/users/**").permitAll()

                                //Address N/A
                                //비회원 주소 없음

                                //Product
                                .requestMatchers("/api/v2/product/**").permitAll()
                                .requestMatchers("/api/v2/products").permitAll()
                                .requestMatchers("/api/v2/random").permitAll()
                                .requestMatchers("/api/v2/ranking").permitAll()
                                .requestMatchers("/api/v2/ranking11").permitAll()

                                //Order N/A
                                //비회원 주문 없음

                                //Cart N/A
                                //비회원 장바구니 없음

                                //Review
                                .requestMatchers("/api/v2/product-review/*/reviews").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v2/product-review/**").permitAll()

                                //Option (permitAll)
                                .requestMatchers("/api/v2/option/**").permitAll()

                                //Like N/A
                                //비회원 좋아요 없음

                                //Coupon
                                .requestMatchers(HttpMethod.GET, "/api/v2/coupon").permitAll()

                                //Category (permitAll)
                                .requestMatchers("/api/v2/category").permitAll()
                                .requestMatchers("/api/v2/category-child").permitAll()
                                .requestMatchers("/api/v2/category-product").permitAll()

                                //Bundle (permitAll)
                                .requestMatchers("/api/v2/bundles").permitAll()
                                .requestMatchers("/api/v2/bundles/**").permitAll()

                                //Brand (permitAll)
                                .requestMatchers("/api/v2/brand").permitAll()
                                .requestMatchers("/api/v2/brand/**").permitAll()

                                //Search
                                .requestMatchers("/api/v2/search").permitAll()
                                .requestMatchers("/api/v2/search-list").permitAll()

                                //Admin (permit All)
                                //현재 Admin 인증 진행 하지 않음
                                //todo : Admin 회원 인증 가능 하면 삭제 필요.
                                .requestMatchers("/api/v2/admin/**").permitAll()

                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(
                        sessionManagement -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(
                        authenticationProvider) //등록할때 사용하는 키는 authenticationProvider를 사용
                .addFilterBefore(jwtTokenProvider,
                        UsernamePasswordAuthenticationFilter.class); //내가 만든 필터 추가

        return http.build();
    }
}
