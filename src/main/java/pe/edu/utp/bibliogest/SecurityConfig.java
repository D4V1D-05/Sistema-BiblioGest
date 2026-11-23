package pe.edu.utp.bibliogest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pe.edu.utp.bibliogest.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthFilter) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // Desactivamos CSRF para APIs REST
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/css/**", "/js/**", "/img/**", "/login", "/api/auth/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/libros/nuevo", "/inventario/ingreso").hasRole("ADMIN")
                        .requestMatchers("/libros", "/lectores", "/prestamos", "/inventario").hasAnyRole("ADMIN", "BIBLIOTECARIO")
                        .requestMatchers("/operaciones", "/novedades", "/contacto").hasAnyRole("ADMIN", "BIBLIOTECARIO", "LECTOR")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler((request, response, authentication) -> {
                            var authorities = authentication.getAuthorities();
                            boolean esLector = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_LECTOR"));

                            if (esLector) {
                                response.sendRedirect("/novedades");
                            } else {
                                response.sendRedirect("/");
                            }
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            if (request.getRequestURI().startsWith("/api/")) {
                                response.setStatus(jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED);
                                response.setContentType("application/json");
                                response.getWriter().write("{\"error\": \"No autorizado. Por favor, proporcione un token JWT válido.\"}");
                            } else {
                                response.sendRedirect("/login");
                            }
                        })
                );

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder encoder) {
        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails bibliotecario = User.builder()
                .username("biblio")
                .password(encoder.encode("biblio123"))
                .roles("BIBLIOTECARIO")
                .build();

        UserDetails lector = User.builder()
                .username("lector")
                .password(encoder.encode("lector123"))
                .roles("LECTOR")
                .build();

        return new InMemoryUserDetailsManager(admin, bibliotecario, lector);
    }
    @Bean
    public org.springframework.security.authentication.AuthenticationManager authenticationManager(org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}