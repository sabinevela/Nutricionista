package com.itsqmet.nutricional.Seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AutorizPaciente {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        // Páginas públicas
                        .requestMatchers("/", "/nosotros", "/formulario", "/registrar", "/formularioNutrologo", "/registrarNutriologo", "/loginPaciente", "/Css/**", "/js/**", "/img/**", "/webjars/**").permitAll()

                        // Acceso exclusivo para PACIENTES
                        .requestMatchers("/paciente/**", "/recetas/**", "/consultas/**").hasRole("PACIENTE")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/loginPaciente")
                        .permitAll()
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/dashboard", true)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
}

