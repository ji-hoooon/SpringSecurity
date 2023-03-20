package com.mino.security1.controller;

import com.mino.security1.config.auth.PrincipalDetails;
import com.mino.security1.model.User;
import com.mino.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//view를 리턴하겠다.
public class IndexController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    //localhost:8080
    @GetMapping({"", "/"})
    public String index(){
        //머스테치 기본폴더 src/main/resources
        //뷰 리졸버 설정 : templates (prefix), .mustache(suffix)
//        #musatche 의존성 추가시 기본값 자동설정

        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user(){
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }
    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "manager";
    }

    //스프링 시큐리티가 해당 주소 필터링
    //: SecurityConfig로 필터설정후 시큐리티가 작동안함
    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }
    @PostMapping("/join")
    public String join(User user){
        System.out.println(user);
        user.setRole("ROLE_USER");
        String rawPassword=user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(rawPassword));
        userRepository.save(user);

//        return "join";
        return "redirect:/loginForm";
        //redirect 이용시 해당 함수를 호출한다. -> 해당 주소로 이동
    }

    //로그인 테스트
    //: Authentication 객체가 가질 수 있는 2가지 타입
    //(1) Authentication을 주입받아서 인증된 사용자 정보로 다운캐스팅
    //(2) @AuthenticationPrincipal + 인터페이스 다형성을 이용해 사용자 정보를 담은 객체 주입
    @GetMapping("/text/login")
    //Authentication 객체 / 현재 인증된 사용자 Principal 정보(인증된 사용자의 세션정보)를 DI
//    public @ResponseBody String loginTest(Authentication authentication, @AuthenticationPrincipal UserDetails userDetails){

    //인터페이스 다형성
    public @ResponseBody String loginTest(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails){
        System.out.println("/text/login");
        System.out.println("authentication = " + authentication);

        //오브젝트 타입인 authentication.getPrincipal()을 캐스팅
        System.out.println("authentication.getPrincipal() = " + authentication.getPrincipal());
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("principalDetails = " + principalDetails);
        System.out.println("principalDetails.getUser() = " + principalDetails.getUser());

        System.out.println("userDetails.getUsername() = " + userDetails.getUsername());

        //인터페이스 다형성
        System.out.println("PrincipalDetails 타입의 userDetails.getUser() = " + userDetails.getUser());
        System.out.println("principalDetails.getUser() = " + principalDetails.getUser());

        return "세션 정보 확인하기";
    }


    //OAuth2 로그인 테스트 : OAuth2으로 로그인할 경우 (PrincipalDetails) authentication.getPrincipal(); 캐스팅 불가능
    //: Authentication 객체가 가질 수 있는 2가지 타입
    //(1) Authentication을 주입받아서 인증된 사용자 정보로 다운캐스팅
    //(2) @AuthenticationPrincipal + 인터페이스 다형성을 이용해 사용자 정보를 담은 객체 주입
    @GetMapping("/text/oauth/login")
    //Authentication 객체 / 현재 인증된 사용자 Principal 정보(인증된 사용자의 세션정보)를 DI
//    public @ResponseBody String loginTest(Authentication authentication, @AuthenticationPrincipal UserDetails userDetails){

    //인터페이스 다형성
    public @ResponseBody String loginOAuthTest(Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth2User2){
        System.out.println("/text/login");
        System.out.println("authentication = " + authentication);

        //오브젝트 타입인 authentication.getPrincipal()을 캐스팅
        System.out.println("authentication.getPrincipal() = " + authentication.getPrincipal());
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        //Authentication을 이용해서 다운캐스팅해서 인증된 사용자정보 가져오기
        System.out.println("oAuth2User.getAttributes() = " + oAuth2User.getAttributes());

        //@AuthenticationPrincipal을 이용해서 인증된 사용자정보 가져오기
        System.out.println("oAuth2User2.getAttributes() = " + oAuth2User2.getAttributes());

        return "OAuth2 세션 정보 확인하기";
    }


//    @GetMapping("/joinProc")
//    public @ResponseBody String joinProc(){
//        return "회원가입 완료됨";
//    }

//    @Secured("ROLE_ADMIN")
//    @GetMapping("/info")
//    public @ResponseBody String info(){
//        return "개인정보";
//    }
//    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_MANAGER')")
//    @GetMapping("/info")
//    public @ResponseBody String data(){
//        return "데이터정보";
//    }
}
