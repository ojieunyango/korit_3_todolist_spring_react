package com.example.todo_backend_mariadb.dto;

import com.example.todo_backend_mariadb.domain.Todo;

public class TodoDto {
    // ✅ 할 일을 새로 만들 때 사용되는 요청 DTO
    // → 사용자가 입력한 텍스트만 필요함 (예: "운동하기")
    public record TodoCreateRequestDto(String text){}

    // ✅ 할 일을 수정할 때 사용되는 요청 DTO
    // → 주로 완료 여부만 수정할 때 사용 (true/false)
    public record TodoUpdateRequestDto(Boolean completed){}

    // ✅ 클라이언트에게 보낼 응답 DTO
    // → 할 일 정보를 화면에 보여줄 때 사용
    //    포함된 정보: id, 할 일 내용, 완료 여부, 작성자 이름
    public record TodoResponseDto(Long id, String text, boolean completed, String author){
        // 엔티티(Todo 객체)를 받아서 응답용 DTO로 변환해주는 생성자
        public TodoResponseDto(Todo entity){
            this(
                    entity.getId(),  // 할 일 ID
                    entity.getText(), // 할 일 내용
                    entity.isCompleted(), // 완료 여부
                    entity.getUser().getName() // 작성자 이름 (User 객체에서 이름 추출)
            );
        }

    }
}
/*
Todo에 수정기능없구 Dto에 수정기능 따로 만듬
보안 & 성능: 필요한 정보만 주고받아서 안전하고 효율적
TodoDto.TodoUpdateRequestDto는 단지 "수정할 데이터만 담는 전달용 객체"이고,
진짜 Todo 객체와 연결돼서 수정되는 건 서비스 레이어
 */
