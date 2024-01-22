package rest.arduino.smartalarm.application.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import rest.arduino.smartalarm.application.service.AuthUserServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        requests ->
                                requests
                                        .requestMatchers("/css/**", "/sigIn", "/signUp")
                                        .permitAll()
                                        .requestMatchers("/photos")
                                        .hasAuthority("USER")
                                        .anyRequest()
                                        .authenticated()
                )
                .formLogin(
                        httpSecurityFormLoginConfigurer ->
                                httpSecurityFormLoginConfigurer
                                        .loginPage("/signIn")
                                        .usernameParameter("nickname")
                                        .passwordParameter("password")
                                        .defaultSuccessUrl("/", true)
                                        .permitAll()
                )
                .logout(
                        logoutCustomizer ->
                                logoutCustomizer
                                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                        .logoutSuccessUrl("/signIn?logout")
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new AuthUserServiceImpl();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

}
