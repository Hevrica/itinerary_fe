package itinerary.itinerary_fe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import itinerary.itinerary_fe.config.jwt.JwtAccessDeniedHandler;
import itinerary.itinerary_fe.config.jwt.JwtAuthenticationEntryPoint;
import itinerary.itinerary_fe.config.jwt.JwtFilter;
import itinerary.itinerary_fe.config.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtProvider jwtProvider;
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private JwtAccessDeniedHandler jwtAccessDeniedHandler;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.httpBasic().disable()
					.csrf().disable()
					.anonymous().disable()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
					.exceptionHandling()
					.authenticationEntryPoint(jwtAuthenticationEntryPoint)
					.accessDeniedHandler(jwtAccessDeniedHandler)
					.and()
					.authorizeHttpRequests()
					//.requestMatchers("/v3/api-docs/**").permitAll()
					//.requestMatchers("/swagger-ui/index.html").permitAll()
					//.requestMatchers("/swagger-resources/**").permitAll()
					//.requestMatchers("/user/**").authenticated()
	                //.requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
	                //.requestMatchers("/admin/**").hasRole("ADMIN")
					//.requestMatchers("/").permitAll()
	                .anyRequest().permitAll()
	                //.and()
	                //.formLogin().loginPage("/login")
	                .and()
	                .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
		
		return httpSecurity.build();
	}
}