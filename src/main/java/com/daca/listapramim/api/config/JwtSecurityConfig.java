package com.daca.listapramim.api.config;

import com.daca.listapramim.api.security.JWTLoginFilter;
import com.daca.listapramim.api.security.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class    JwtSecurityConfig extends WebSecurityConfigurerAdapter {

    private SecurityFilter securityFilter;

    @Autowired
    public JwtSecurityConfig(SecurityFilter securityFilter){
        this.securityFilter = securityFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/user/login").permitAll()
                .antMatchers(HttpMethod.GET, "/v2/api-docs").permitAll()
                .antMatchers(HttpMethod.GET, "/configuration/ui/**").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-resources/**").permitAll()
                .antMatchers(HttpMethod.GET, "/configuration/exceptions/**").permitAll()
                .antMatchers(HttpMethod.GET, "/webjars/**").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-ui.html/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected  void configure(AuthenticationManagerBuilder auth) throws  Exception{
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("password")
                .roles("ADMIN");
    }
}
