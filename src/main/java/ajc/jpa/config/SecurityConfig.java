package ajc.jpa.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import ajc.jpa.models.CustomRole;
import ajc.jpa.models.CustomUser;
import ajc.jpa.repositories.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	UserDetailsService userDetailsService;
	
	@Value("${spring.h2.console.path}")
    private String h2ConsolePath;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/home/**", "/register/**", "", "/").permitAll()
                        .requestMatchers(h2ConsolePath + "/**").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.permitAll())
                .logout(logout -> logout.permitAll())
                .userDetailsService(userDetailsService)
                .headers(headers -> headers.frameOptions().sameOrigin());
        return http.build();
    }
    
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            CustomRole roleAdmin = new CustomRole("ROLE_ADMIN");
            CustomRole roleUser = new CustomRole("ROLE_USER");
            CustomUser user = new CustomUser("user",
                    passwordEncoder().encode("pass"),
                    List.of(roleAdmin, roleUser)
            );
            userRepository.save(user);
        };
    }

}
