package com.example.todo_backend_mariadb.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
// 데이터베이스의 테이블과 연결되는 객체임을 알려주는 어노테이션
@Entity //DB 랑 연결되는 JPA 엔티티
// 자동으로 getter/setter, toString, equals, hashCode 등을 만들어주는 롬복 어노테이션
@Data // 종합 패키지 -> getter/setter alt+ins 로 안만들어도 됨
// 모든 필드를 사용하는 생성자 자동 생성
@AllArgsConstructor
// 파라미터가 없는 기본 생성자 자동 생성
@NoArgsConstructor
// 빌더 패턴을 사용해서 객체 생성 가능하게 해줌
@Builder
public class Todo {
    // 이 필드는 기본키임을 나타냄
    @Id
    // 기본키 값을 데이터베이스가 자동으로 생성하도록 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 이 필드는 테이블의 기본 키이고, 값은 DB가 자동으로 생성해줌(자동 증가)
    // null 값 불가, 수정도 불가능하도록 설정 (아이디는 한 번 정해지면 안 바뀜)
    @Column(nullable = false, updatable = false)
    private Long id; //할 일 고유 번호 (DB가 자동으로 만들어 줌)

    // 텍스트 내용이 반드시 있어야 하고, 길이가 긴 텍스트도 저장 가능하도록 TEXT 타입으로 설정
    @Column(nullable = false, columnDefinition = "TEXT")
    private String text; //할 일 내용

    // 완료 여부를 저장하는 필드로, 반드시 값이 있어야 함 (true 또는 false)
    @Column(nullable = false)
    private boolean completed; //할 일을 끝냈는지 여부

    //User 엔티티와의 연관관계 추가
    // 여러 할 일(Todo)들이 한 명의 사용자(User)에 속할 수 있음을 의미하는 관계 설정
    // Lazy 로딩은 실제로 사용할 때까지 User 정보를 불러오지 않음 (속도 향상)
    @ManyToOne(fetch = FetchType.LAZY)
    // 데이터베이스에서 이 필드가 user 테이블의 id 컬럼과 연결됨을 나타냄
    @JoinColumn(name = "user_id")
    // 이 할 일을 만든 사용자 정보
    private User user;
}
