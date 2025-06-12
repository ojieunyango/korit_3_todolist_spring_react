package com.example.todo_backend_mariadb.service;

import com.example.todo_backend_mariadb.domain.*;
import com.example.todo_backend_mariadb.dto.TodoDto.*;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // 생성자 자동 생성 (todoRepository, userRepository 자동 주입)
@Transactional // DB 작업은 트랜잭션 안에서 처리됨 (성공하면 저장, 실패하면 롤백)
public class TodoService {

    // 할 일 관련 DB 작업을 위한 리포지토리
    private final TodoRepository todoRepository;
    // 사용자 관련 DB 작업을 위한 리포지토리
    private final UserRepository userRepository;
    // ✅ [할 일 생성] - 사용자가 새로운 할 일을 추가하면 실행됨
    @Transactional
    public TodoResponseDto createTodo(TodoCreateRequestDto requestDto, String userEmail){
        // 이메일로 사용자 찾기 → 없으면 새로 저장 (자동 회원가입 느낌)
        User user = userRepository.findByEmail(userEmail)
                .orElseGet(()->userRepository.save(User.builder()
                        .name(userEmail.split("@")[0])
                        .email(userEmail)
                        .role(Role.USER)
                        .build()));
        // 새 할 일 만들기 (text만 입력받고, completed는 기본 false)
        Todo todo = Todo.builder()
                .text(requestDto.text())
                .completed(false)
                .user(user)
                .build();
        // 저장하고 → TodoResponseDto로 변환해서 리턴
        return new TodoResponseDto(todoRepository.save(todo));
    }
    // ✅ [내 할 일 목록 가져오기] - 로그인한 사용자의 할 일 전체 조회
    public List<TodoResponseDto> findMyTodos(String userEmail){
        User user = findByEmail(userEmail); // 사용자 정보 찾기
        // 최신순 정렬
        return todoRepository.findByUserOrderByIdDesc(user)
                // 리스트 하나하나 반복해서
                .stream()
                // Todo를 TodoResponseDto로 바꿈
                .map(TodoResponseDto::new)
                // 리스트로 모아서 반환
                .collect(Collectors.toList());
    }

    // ✅ [완료 상태 토글] - 할 일을 클릭했을 때 완료/미완료 전환
    public TodoResponseDto toggleTodo(Long id, String userEmail){
        Todo todo = findByIdAndUserEmail(id, userEmail); // 해당 Todo 찾고
        todo.setCompleted(!todo.isCompleted()); // 완료 상태 반대로 바꾸기 (true ↔ false)
        return new TodoResponseDto(todo); // 바뀐 결과를 DTO로 반환
    }

    // ✅ [할 일 삭제] - 사용자가 본인 할 일을 삭제할 때
    public void deleteTodo(Long id, String userEmail){
        // 본인 소유인지 확인
        Todo todo = findByIdAndUserEmail(id, userEmail);
        todoRepository.delete(todo); // 삭제
    }

    // ⚠️ 내부 메서드: 아이디 + 이메일로 할 일 찾기 (다른 사람 할 일은 못 건드리게 보안처리)
    private Todo findByIdAndUserEmail(Long id, String userEmail) {
        return todoRepository.findById(id)
                .filter(todo->todo.getUser().getEmail().equals(userEmail)) // 사용자가 소유한 할 일만 허용
                .orElseThrow(()->new IllegalArgumentException("존재하지않거나 권한이 없는 Todo입니다."));

    }
    // ⚠️ 내부 메서드: 이메일로 사용자 찾기, 없으면 생성
    private User findByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail)
                .orElseGet(()->userRepository.save(User.builder()
                        .name(userEmail.split("@")[0])
                        .email(userEmail)
                        .role(Role.USER)
                        .build()));
    }
}
