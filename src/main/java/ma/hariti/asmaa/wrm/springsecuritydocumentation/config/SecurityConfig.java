package ma.hariti.asmaa.wrm.springsecuritydocumentation.config;

import lombok.RequiredArgsConstructor;
import ma.hariti.asmaa.wrm.springsecuritydocumentation.repository.UserRepository;
import ma.hariti.asmaa.wrm.springsecuritydocumentation.service.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRepository userRepository;
    private final DataSource dataSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/register", "/api/users/login", "/h2-console/**", "/api/public/**", "/error").permitAll()
                        .requestMatchers("/api/users/**").hasRole("ADMIN")
                        .requestMatchers("/api/**").hasAuthority("ROLE_USER")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return new JdbcUserDetailsManager(dataSource) {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                ma.hariti.asmaa.wrm.springsecuritydocumentation.entity.User user =
                        userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                return User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .disabled(false)
                        .roles("USER")
                        .build();
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
