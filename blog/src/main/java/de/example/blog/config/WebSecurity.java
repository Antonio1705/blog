package de.example.blog.config;

import de.example.blog.security.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    @Autowired
    private UserDetailService userDetailService;

    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers("/resources/**").permitAll()
                            .requestMatchers("/register/**").permitAll()
                            .requestMatchers("/api/**").hasRole("ADMIN")
                            .requestMatchers("/comment/**").hasAnyRole("USER","ADMIN")
                            .requestMatchers("/").hasAnyRole("USER","ADMIN")
                            .requestMatchers("/blog/**").hasRole("USER");

                })
                .formLogin(form -> form.loginPage("/login")
                        .defaultSuccessUrl("/blog")
                        .loginProcessingUrl("/login")
                        .permitAll())
                .logout(logout ->{
                    logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll();
                });

        return httpSecurity.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder());

    }
}
