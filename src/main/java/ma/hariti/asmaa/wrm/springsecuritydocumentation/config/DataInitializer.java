package ma.hariti.asmaa.wrm.springsecuritydocumentation.config;

import lombok.RequiredArgsConstructor;
import ma.hariti.asmaa.wrm.springsecuritydocumentation.entity.User;
import ma.hariti.asmaa.wrm.springsecuritydocumentation.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    private final UserRepository userRepository;
    final private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner init() {
        return args -> {
            if (userRepository.count() == 0) {
                User dbUser = new User();
                dbUser.setUsername("dbuser");
                dbUser.setPassword(passwordEncoder.encode("password"));
                userRepository.save(dbUser);
            }
        };
    }
}
