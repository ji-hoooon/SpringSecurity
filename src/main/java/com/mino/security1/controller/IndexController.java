package com.mino.security1.controller;

import com.mino.security1.model.User;
import com.mino.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

//    @GetMapping("/joinProc")
//    public @ResponseBody String joinProc(){
//        return "회원가입 완료됨";
//    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_MANAGER')")
    @GetMapping("/info")
    public @ResponseBody String data(){
        return "데이터정보";
    }
}
