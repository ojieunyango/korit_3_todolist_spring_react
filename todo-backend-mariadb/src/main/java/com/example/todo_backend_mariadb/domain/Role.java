package com.example.todo_backend_mariadb.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반사용자");

    // 권한을 구분하기 위한 내부 코드 (예: Spring Security에서 권한 검사할 때 사용)
    private final String key;
    // 사람이 읽기 편한 권한 이름 (예: 화면에 표시할 때 사용)
    private final String title;

}

// enum 이란?? 미리 정해진 상수 값들을 한 곳에 묶어서 표현하는 역할, 각 값마다 관련된 데이터나 기능
//웹사이트에서 주로 회원등급 열거할때 많이 쓰임