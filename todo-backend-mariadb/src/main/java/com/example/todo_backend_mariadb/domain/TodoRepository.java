package com.example.todo_backend_mariadb.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

//spring data rest를 통해서 rest api로 자동노출설정
//@RepositoryRestResource(collectionResourceRel = "todos", path="todos")
// JpaRepository를 상속받으면 CRUD 기능이 자동으로 제공됨 Todo 를 통해 상속받음 id가 long
public interface TodoRepository extends JpaRepository<Todo, Long> {
    //CrudRepository를 상속받기 때문에 기본적인 데이터베이스 작업 메서드들이 이미 구현되어있음
    //ex) save(Todo entity), findById(Long id), findAll(), deleteById(Long id)등

    // 사용자(user)가 작성한 할 일 목록을 id 기준으로 내림차순 정렬해서 가져오는 메서드
    List<Todo> findByUserOrderByIdDesc(User user);
}


//이 인터페이스는 할 일 목록(Todo)을 데이터베이스에서 저장하거나 불러오는 작업을 쉽게 할 수 있도록 도와줌.
//또한, 특정 사용자가 만든 할 일만 뽑아서 최신 순으로 정렬하는 기능도 포함되어있음.