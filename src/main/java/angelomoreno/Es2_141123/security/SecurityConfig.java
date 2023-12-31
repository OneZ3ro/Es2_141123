package angelomoreno.Es2_141123.security;

import angelomoreno.Es2_141123.exceptions.ExceptionsHandlerFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    JWTAuthFilter jwtAuthFilter;
    @Autowired
    ExceptionsHandlerFilter exceptionsHandlerFilter;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // le sessioni sono senza stato
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable()); //da maggiore sicurezza, ma per ora andrebbe solo a mettere i bastoni tra le ruote
        http.formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable()); // disabilita il form login che crea in automatico
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // aggiungo il filtro che mi sono creato
        http.addFilterBefore(exceptionsHandlerFilter, JWTAuthFilter.class); // aggiungo prima di JWTAuthFilter il  filtro delle eccezioni
        http.authorizeHttpRequests(request -> request.requestMatchers("/**").permitAll()); // aggiungo/rimuovo protezione per cui venga/non venga richiesto l'autenticazione su tutti gli endopoint
        return http.build();
    }

    @Bean
    PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
