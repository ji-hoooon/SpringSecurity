package com.mino.security1.config.auth;

import com.mino.security1.model.User;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;


//시큐리티가 /login 주소 요청이 오면 해당 주소를 필터링해서 로그인을 진행
//로그 진행이 완료되면 시큐리티만의 session 생성 (키가 Security ContextHolder)
//시큐리티가 가지고 있는 사용자의 세션정보를 가지고 있는 객체 : Authentication 객체
//Authentication 객체 안에는 유저 정보가 들어 있다.
//: 유저 정보의 타입은 UserDetails 타입 객체

//SecurityContextHolder -> Security Context -> Authentication -> UserDetails


@Data
public class PrincipalDetails implements UserDetails {

    private User user; //컴포지션 - 포함관계로 추가

    //생성자로 의존성 주입
    public PrincipalDetails(User user) {
        this.user = user;
    }

    //해당 User의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //실제 유저의 권한
//        user.getRole();
        //:String이므로 필요한 타입으로 캐스팅
        Collection<GrantedAuthority> collect=new ArrayList<>();
        collect.add(()->user.getRole());
//        collect.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return user.getRole();
//            }
//        });

        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        //1년 동안 로그인 안하면 false 처리해서 휴면 계정으로 처리
        //현재 시간 -마지막 로그인 시간 => 1년 초과하면 return false;
        return true;
    }
}
