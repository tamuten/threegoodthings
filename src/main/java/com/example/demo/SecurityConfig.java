package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers("/webjars/**", "/css/**", "/js/**", "/error");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // jsとcss、新規登録画面はだけでも遷移可能
        http.authorizeRequests()
            .antMatchers("/webjars/**", "/css/**", "/js/**", "/signup", "/signup/**", "/reissue/**")
            .permitAll()
            .anyRequest()
            .authenticated();

        // ログイン
        http.formLogin()
            .loginPage("/login")
            .usernameParameter("mailaddress")
            .passwordParameter("password")
            .defaultSuccessUrl("/index")
            .failureUrl("/login?error")
            .permitAll()
            .and()
            .logout()
            .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            //userDetailsServiceを使って、認証を行う
            .userDetailsService(userDetailsService);
    }

}
