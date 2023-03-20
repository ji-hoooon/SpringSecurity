package com.mino.security1.config.auth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    //구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
    //super.loadUser(userRequest) 정보를 이용해 자동 회원가입
    //: username, password, email, role, provider, providerId
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest.getClientRegistration() = " + userRequest.getClientRegistration());
        //userRequest.getClientRegistration().getRegistrationId()로 인증주체 확인 가능

        System.out.println("userRequest.getAccessToken() = " + userRequest.getAccessToken());
        System.out.println("userRequest.getAccessToken().getTokenValue() = " + userRequest.getAccessToken().getTokenValue());
        System.out.println("userRequest.getClientRegistration().getClientId() = " + userRequest.getClientRegistration().getClientId());
        System.out.println("userRequest.getClientRegistration().getProviderDetails() = " + userRequest.getClientRegistration().getProviderDetails());

        //구글 로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인 완료 -> code를 리턴 (OAuth-client라이브러리) -> AccessToken 요청
        //userReqeust 정보 -> loadUser 메서드 호출-> 인증 주체로부터 회원 프로필 정보 가져오기
        System.out.println("super.loadUser(userRequest).getAttributes() = " + super.loadUser(userRequest).getAttributes());
        OAuth2User oAuth2User = super.loadUser(userRequest);

        return super.loadUser(userRequest);
    }
}
