
package gr.aueb.gr.appointment.security;

import gr.aueb.gr.appointment.security.jwt.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {}) // ✅ ΠΡΟΣΘΗΚΗ

                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()

                        // Public reads
                        .requestMatchers(HttpMethod.GET, "/api/appointments", "/api/appointments/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categories", "/api/categories/**").permitAll()

                        // Owner writes
                        .requestMatchers(HttpMethod.POST, "/api/appointments", "/api/appointments/**").hasRole("OWNER")
                        .requestMatchers(HttpMethod.PUT, "/api/appointments", "/api/appointments/**").hasRole("OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/api/appointments", "/api/appointments/**").hasRole("OWNER")

                        .requestMatchers(HttpMethod.POST, "/api/categories", "/api/categories/**").hasRole("OWNER")
                        .requestMatchers(HttpMethod.PUT, "/api/categories", "/api/categories/**").hasRole("OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/api/categories", "/api/categories/**").hasRole("OWNER")
                        .requestMatchers("/error").permitAll()

                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    //  Cors allow για Vite dev server
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(List.of("http://localhost:5173"));
        cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        cfg.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        cfg.setExposedHeaders(List.of("Authorization"));
        cfg.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        var owner = User.withUsername("owner")
                .password(encoder.encode("1234"))
                .roles("OWNER")
                .build();
        return new InMemoryUserDetailsManager(owner);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

