📁 src/main/java
├── controller              // [클래스] HTTP 요청 받아 Service 호출, 응답 반환
│   ├── MainController          // 메인 페이지 API
│   ├── AuthController          // 로그인, 회원가입 API
│   ├── MypageController        // 내 정보, 내 여행, 공유 여행 조회 API
│   ├── PlannerController       // 여행 계획 생성/조회/수정/삭제 API
│   └── CommunityController     // 공유 게시판 API

├── service                 // [인터페이스 + 구현 클래스] 핵심 비즈니스 로직
│   ├── UserService             // (interface) 사용자 관련 처리 정의
│   ├── UserServiceImpl         // (class) 사용자 로직 구현
│   ├── PlanService             // (interface) 여행 계획 처리 정의
│   ├── PlanServiceImpl         // (class) 여행 계획 로직 구현
│   ├── CommunityService        // (interface) 게시판 처리 정의
│   └── CommunityServiceImpl    // (class) 게시판 로직 구현

├── repository             // [인터페이스] JPA를 통한 DB 작업 정의
│   ├── UserRepository          // 사용자 테이블 연동
│   ├── PlanRepository          // 여행 계획 테이블 연동
│   └── CommunityRepository     // 게시글 테이블 연동

├── model (또는 domain)    // [클래스 + enum] DB 테이블과 매핑되는 Entity
│   ├── User                   // 사용자 Entity (id, 이름, 이메일 등)
│   ├── TravelPlan             // 여행 계획 Entity (제목, 날짜, 장소 등)
│   ├── Post                   // 게시판 글 Entity
│   ├── Role (enum)            // 사용자 권한 (USER, ADMIN 등)
│   └── PlanVisibility (enum)  // 계획 공개 범위 (PUBLIC, PRIVATE 등)

├── dto                   // [클래스] 요청/응답용 데이터 전달 객체
│   ├── UserDto               // 사용자 정보 DTO
│   ├── PlanDto               // 여행 계획 DTO
│   └── PostDto               // 게시판 DTO

📁 src/main/resources
├── application.properties   // [설정 파일] DB 연결, 서버 포트 등 환경 설정
└── static/ or templates/    // (선택) 뷰 파일 위치 - 백엔드만 개발 시 생략 가능
