package com.example.todo_backend_mariadb.web;

import com.example.todo_backend_mariadb.dto.TodoDto.*;
import com.example.todo_backend_mariadb.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;  // 실제 할 일 처리하는 서비스 클래스 호출용
    // 내 할 일 목록 조회 요청 처리 (GET /api/todos)
    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> getMyTodos(@AuthenticationPrincipal Jwt principal){
        // JWT 토큰에서 사용자 이메일을 꺼내서 내 할 일 목록을 찾아서 반환
        return ResponseEntity.ok(todoService.findMyTodos(principal.getClaimAsString("email")));
    }
    // 새로운 할 일 생성 요청 처리 (POST /api/todos)
    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoCreateRequestDto requestDto, // 클라이언트가 보낸 할 일 내용 받음
                                                      @AuthenticationPrincipal Jwt principal){ // 로그인 사용자 정보(JWT) 받음
        // 할 일을 생성하고, 생성된 할 일 데이터를 응답으로 보냄 (HTTP 201 Created)
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.createTodo(
                requestDto,
                principal.getClaimAsString("email")));
    }
    // 할 일 완료 상태 토글 요청 처리 (PATCH /api/todos/{id})
    @PatchMapping("/{id}")
    public ResponseEntity<TodoResponseDto> toggleTodo(@PathVariable Long id, // URL 경로에서 할 일 ID 받음
                                                      @AuthenticationPrincipal Jwt principal){  // 로그인 사용자 정보(JWT) 받음
        // 해당 ID의 할 일 완료 상태를 반전시키고, 변경된 할 일 데이터를 응답으로 보냄
        return ResponseEntity.ok(todoService.toggleTodo(id, principal.getClaimAsString("email")));
    }
    // 할 일 삭제 요청 처리 (DELETE /api/todos/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id,  // URL 경로에서 할 일 ID 받음
                                           @AuthenticationPrincipal Jwt principal){ // 로그인 사용자 정보(JWT) 받음
        // 해당 ID의 할 일을 삭제하고, 응답 본문 없이 상태코드 204(No Content)만 반환
        todoService.deleteTodo(id, principal.getClaimAsString("email"));
        return ResponseEntity.noContent().build();
    }
}
