package com.mino.security1.config.auth;

import com.mino.security1.model.User;
import com.mino.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    //회원가입을 위한 User 작업시
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;


    //구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
    //super.loadUser(userRequest) 정보를 이용해 자동 회원가입
    //: username, password, email, role, provider, providerId
    //함수종료시 @AuthenticationPrincipal 어노테이션으로 Authentication 객체 반환 가능
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

        //PrincipalUserDetails 에서 구현할 클래스인 OAuth2 추가한 뒤, 회원가입 진행하는 후처리 작업 수행
        String provider =userRequest.getClientRegistration().getClientId(); //google
        String providerId =oAuth2User.getAttribute("sub");
        String username = provider+"-"+providerId;
        //인코딩작업 클래스를 주입받아서 사용
        String password=bCryptPasswordEncoder.encode("asdf");
        String email = oAuth2User.getAttribute("email");
        String role="ROLE_USER";

        //이미 회원가입 여부 체크를 위해 UserRepository 주입 받아서 체크
        User userEntity=userRepository.findByUsername(username);
        if(userEntity==null){
            System.out.println("구글로 최초 로그인");
            userEntity=User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }
        System.out.println("구글 이전에 로그인");
//        return super.loadUser(userRequest);
        //일반 로그인시 User, 소셜로그인시 User, Attributes 리턴
        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}
