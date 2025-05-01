package pe.edu.utp.minimarket.Infra.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private SecurityFilter securityFilter;

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .cors(cors -> {})
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/images/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/images/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/categorias/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/productos/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/inventarios/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll()
            .requestMatchers("/auth/**", "/usuarios/**", "/roles/**").permitAll()
            .requestMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/**").authenticated()
            .requestMatchers(HttpMethod.POST, "/**").authenticated()
            .requestMatchers(HttpMethod.PUT, "/**").authenticated()
            .requestMatchers(HttpMethod.DELETE, "/**").authenticated()
            .anyRequest().authenticated()
        )
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
