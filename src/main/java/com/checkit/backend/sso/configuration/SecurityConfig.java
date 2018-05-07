package com.checkit.backend.sso.configuration;

import com.checkit.backend.sso.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Created by Gleb Dovzhenko on 22.04.2018.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.prefix}")
    private String jwtPrefix;
    @Value("${jwt.header}")
    private String jwtHeader;

    private final SsoAuthenticationEntryPoint authEntryPoint;
    private final SsoAuthenticationSuccessHandler authenticationSuccessHandler;
    private final SsoAuthenticationFailureHandler authenticationFailureHandler;
    private final SsoAuthenticationProvider authenticationProvider;


    @Autowired
    public SecurityConfig(SsoAuthenticationEntryPoint authEntryPoint,
                          SsoAuthenticationSuccessHandler authenticationSuccessHandler,
                          SsoAuthenticationFailureHandler authenticationFailureHandler,
                          SsoAuthenticationProvider authenticationProvider) {

        this.authEntryPoint = authEntryPoint;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .authorizeRequests()
                .antMatchers("/api/signup/**","api/signin/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .exceptionHandling().authenticationEntryPoint(authEntryPoint)

                .and()
                .addFilter(authenticationFilter())
                .addFilter(new JwtAuthorizationFilter(
                                    authenticationManager(),jwtSecret,jwtPrefix,jwtHeader));

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    public JwtAuthenticationFilter authenticationFilter() throws Exception {
        JwtAuthenticationFilter authenticationFilter
                = new JwtAuthenticationFilter();
        authenticationFilter.setAuthenticationSuccessHandler(this.authenticationSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(this.authenticationFailureHandler);
        authenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/signin", "POST"));
        authenticationFilter.setAuthenticationManager(authenticationManager());
        return authenticationFilter;
    }

    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
