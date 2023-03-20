package com.mino.security1.config;

import com.mino.security1.config.auth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
//securedEnabled: 특정 메서드에 @Secure 어노테이션을 붙여서 명시한 권한을 가진 사용자만
// 호출을 가능하게 하는 어노테이션
//prePostEnabled : 특정 메서드에 @PreAuthorize 어노테이션을 붙여서 메서드 실행 전에
// 특정 조건을 만족하는지 검사를 가능하게하는 어노테이션
public class SecurityConfig {
    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

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
                .loginPage("/loginForm")
        //UserDetailsService에 전달되는 인자명을 바꿀 경우
//                .usernameParameter("username2");

        //만약 접근 권한이 없는 페이지 이동시 로그인 폼으로 이동되도록 하는데
        //로그인 페이지를 커스텀 로그인 페이지로 설정한다.
                .loginProcessingUrl("/login")
        //login 주소가 호출되면 시큐리티가 필터링해서 대신 로그인 진행
                .defaultSuccessUrl("/")
        //로그인 성공시 인덱스 페이지로 이동
                .and()
                .oauth2Login()
                .loginPage("/loginForm")
        //OAuth2 로그인 설정 -> 로그인페이지는 동일하게 설정
                .userInfoEndpoint()
                .userService(principalOauth2UserService);
        //로그인 완료 (인증 처리 완료) -> 후처리 작업
        //OAuth2 client 라이브러리는 액세스 토큰과 사용자 프로필 정보를 자동으로 처리한다.

        return http.build();
    }


}
