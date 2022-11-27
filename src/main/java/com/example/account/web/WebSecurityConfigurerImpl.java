package com.example.account.web;

import com.example.account.models.listeners.CustomAccessDeniedHandler;

import com.example.account.models.listeners.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandler;

import static com.example.account.utils.Utility.getEncoder;

@EnableWebSecurity
public class WebSecurityConfigurerImpl extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;


    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    @Autowired
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;


    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .userDetailsService(userDetailsService) // user store 1
                .passwordEncoder(getEncoder());

    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .csrf().disable().headers().frameOptions().disable()
                .and()
                .authorizeRequests()// (1)
                .mvcMatchers(HttpMethod.POST,"/api/auth/signup").permitAll()
                .mvcMatchers(HttpMethod.GET,"/api/empl/payment").hasAnyAuthority("USER","ACCOUNTANT")
                .mvcMatchers(HttpMethod.POST,"api/auth/changepass").hasAnyAuthority("ACCOUNTANT","USER","ADMINISTRATOR")
                .mvcMatchers(HttpMethod.POST,"/api/acct/payments").hasAuthority("ACCOUNTANT")
                .mvcMatchers(HttpMethod.PUT,"/api/acct/payments").hasAuthority("ACCOUNTANT")
                .mvcMatchers(HttpMethod.DELETE,"/api/admin/user/*").hasAuthority("ADMINISTRATOR")
                .mvcMatchers(HttpMethod.GET,"/api/admin/user").hasAuthority("ADMINISTRATOR")
                .mvcMatchers(HttpMethod.PUT,"/api/admin/user/role").hasAuthority("ADMINISTRATOR")
                .mvcMatchers(HttpMethod.PUT,"/api/admin/user/access").hasAuthority("ADMINISTRATOR")
                .mvcMatchers(HttpMethod.GET,"/api/security/events").hasAuthority("AUDITOR")
                .and()
                .formLogin()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler());
                // (3)
    }
}
