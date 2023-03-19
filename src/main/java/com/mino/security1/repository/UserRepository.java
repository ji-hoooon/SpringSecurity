package com.mino.security1.repository;

import com.mino.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//CRUD 함수를 JpaRepository가 들고 있다.
//@Repository 어노테이션이 없어도, JpaRepository를 상속했기 때문에 IoC가 되어 스프링컨테이너에 등록이 된다.
public interface UserRepository extends JpaRepository<User, Integer> {
}
