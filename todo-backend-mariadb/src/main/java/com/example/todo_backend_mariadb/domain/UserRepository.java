package com.example.todo_backend_mariadb.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    // 이메일 주소로 사용자를 찾아주는 메서드
    //사용자가 있을 수도, 없을 수도 있기 때문에 null 대신 Optional로 감싸는거
    //(예외 처리에 유리함)
    Optional<User> findByEmail(String email);
}
