package com.mino.security1.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.servlet.Filter;

@Configuration
//@EnableWebSecurity
//  스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.

//WebSecurityConfigurerAdapter
public class SecurityConfig {

    //해당 메서드의 리턴되는 오브젝트를 IoC로 등록된다.
    @Bean
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }

//    @Override
//    public void configure(SecurityBuilder builder) throws Exception {
//        HttpSecurity http= (HttpSecurity) builder;
//        http.csrf().disable();
//        http.authorizeRequests()
//                .antMatchers("/user/**").authenticated()
//                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or  hasRole('ROLE_MANAGER')")
//                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
//        //권한이 적은 사용자부터 권한이 많은 순으로
//                .anyRequest().permitAll();
//        //그외에는 모두 허용
//    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or  hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
        //권한이 적은 사용자부터 권한이 많은 순으로
                .anyRequest().permitAll()
        //그외에는 모두 허용
                .and()
                .formLogin()
                .loginPage("/loginForm");
        //만약 접근 권한이 없는 페이지 이동시 로그인 폼으로 이동되도록 하는데
        //로그인 페이지를 커스텀 로그인 페이지로 설정한다.

        return http.build();
    }


}
