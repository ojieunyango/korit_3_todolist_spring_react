package com.example.todo_backend_mariadb.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
// 이 클래스는 DB 테이블과 연결되는 JPA 엔티티임을 나타냄
@Entity
@Table(name = "users") // user는 DB상에서 예약어라서 오류가 발생할 가능성↑ / car에서는 AppUser였죠
@NoArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 기본 키(id)는 자동 증가 방식으로 DB에서 생성됨
    private Long id;

    @Column(nullable = false)
    // name 필드는 반드시 값이 있어야 함 (null 불가)
    private String name;

    @Column(nullable = false, unique = true)
    // email 필드는 반드시 있어야 하고, 중복도 불가능 (로그인용으로 자주 쓰이니까!)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    // Role이라는 enum 타입을 문자열로 저장 (예: "USER", "GUEST") 사용자권한
    private Role role;

    @Builder        // 생성자에 @Builder 애너테이션 적용 예시 / 클래스 레벨이 아니라.
    // 이 생성자는 빌더 패턴을 사용할 수 있도록 함 (new User(...) 대신 User.builder() 사용)
    public User(String name, String email, Role role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // 사용자 이름을 바꾸고 자기 자신(User 객체)을 반환함 (체이닝 가능하게 만듦)
    public User update(String name) {
        this.name = name;
        return this;
    }
}

/*  Todo와 연결되어있음
    @ManyToOne(fetch = FetchType.LAZY)
    // 데이터베이스에서 이 필드가 user 테이블의 id 컬럼과 연결됨을 나타냄
    @JoinColumn(name = "user_id")
    // 이 할 일을 만든 사용자 정보
    private User user; */