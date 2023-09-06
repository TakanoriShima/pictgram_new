package com.example.pictgram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.pictgram.filter.FormAuthenticationProvider;

@Configuration
public class SecurityConfig  {

    protected static Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    private FormAuthenticationProvider authenticationProvider;
    
    public SecurityConfig(FormAuthenticationProvider authenticationProvider) {
    	this.authenticationProvider = authenticationProvider;
    }

    private static final String[] URLS = { "/css/**", "/images/**", "/scripts/**", "/h2-console/**", "/users/new", "/user", "/" };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	// @formatter:off
    	http.authorizeHttpRequests(authz -> authz
                .requestMatchers(URLS)    // 認証不要なパスを指定
                .permitAll()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()) // css,jsなどのリソースも認証不要
                .permitAll()
                .requestMatchers("/login")
                .permitAll()
                .anyRequest().authenticated())  // antMatchersで指定したパス以外認証する
        .formLogin(login -> login
                .loginProcessingUrl("/login")  // ログイン情報の送信先
                .loginPage("/login")           // ログイン画面
                .defaultSuccessUrl("/topics")        // ログイン成功時の遷移先
                .failureUrl("/login-failure")    // ログイン失敗時の遷移先
                .permitAll())                  // 未ログインでもアクセス可能
        .logout(logout -> logout
                .logoutSuccessUrl("/logout-complete")    // ログアウト成功時の遷移先
                .permitAll())
        .cors(cors -> cors.disable())
        .csrf((csrf) -> csrf.disable());
    	// @formatter:on
    	
        return http.build();
    }


    @Bean
    public FormAuthenticationProvider userDetailsService() {
        return new FormAuthenticationProvider();
    }
    
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        return authenticationManagerBuilder.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}