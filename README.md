# Mission_최주오
### 제목
익명 의견 교환 웹 페이지
### 프로젝트 개요
사용자들이 자기 자신의 정보를 직접 드러낼 필요 없이 의견을 교환할 수 있는 웹페이지
### 구현 기능
1. 게시판 관련 기능
2. 게시물 관련 기능
3. 댓글 관련 기능
### 사용 기술
- Java
- Spring Boot
- JPA - Hibernate
- SQLite
- Thymeleaf
- Lombok

## 기능 설명
### 게시물
1. `Notice` Entity : 게시물 객체를 생성하기 위한 `@Entity`

- `id` : PK
- `title` : 게시물 제목
- `type` : 게시판 타입
- `password` : 비밀번호

2. `NoticeService` : `Notice`객체 가공을 위한 `@Service`
- `create` : 새로은 `Notice`객체를 만들고 속성값 부여
- `readOne` : `id`값으로 `Notice`반환
- `readAll` : db에 저장된 모든 `Notice`반환, 최신순으로 불러오기 위해 `reverse` 수행
- `findByType` : 게시판별 게시물을 불러오기 위해 type이 일치하는 모든 게시물 반환
- `update` : `Notice`속성값 수정, 이때 비밀번호를 확인하고 같은 경우만 수정
- `delete` : `Notice`삭제, 이때 비밀번호를 확인하고 같은 경우만 삭제

3. `NoticeController` : 게시판 관련 사용자 입력 처리
- `home` : 홈페이지를 보여주기 위한 메소드
    - 게시판별 링크와 게시물 작성 링크가 담긴 `home.html`반환
- `createView` : 새로운 게시물을 작성하는 페이지를 보여주기 위한 메소드
    - 게시물을 생성할 수 있는 `create.html`반환
- `create` : `create.html`의 Post요청으로 `Notice`를 생성하기 위한 메소드
    - `/create`에 전달된 파라미터로 `Notice`객체 생성
    - 다시 첫화면(홈페이지)으로 돌아가기 위해 `home.html`반환
- `typeBoard` : 게시판별 게시물을 모아볼 수 있는 페이지를 보여주기 위한 메소드
    - 요청된 링크의 `type`으로 해당 게시판 `board.html`반환
    - 전체 게시판의 경우 `readAll()`를 사용하여 모든 게시물을 `model`에게 전달
    - 특정 게시판의 경우 `findByType()`으로 요청된 `type`과 일치하는 게시물만 `model`에게 전달
- `read` : 게시물 단일 조회 페이지를 보여주기 위한 메소드
    - 요청된 링크의 `id`로 해당 게시물 `read.html`반환
- `updateView` : 게시물 수정 페이지를 보여주기 위한 메소드
    - 요청된 링크의 `id`로 해당 게시물 `update.html`반환
    - 기존 값들을 유지하기 위해 `model`에게 속성값 전달
- `update` : `update.html`의 Post요청으로 `Notice`의 속성값을 수정하기 위한 메소드
    - `/update`에 전달된 파라미터로 해당하는 `id`를 가진 `Notice`속성값 수정
    - 수정된 내용을 바로 확인하기 위해 해당 게시물의 `read.html`반환
- `delete` : `read.html`의 Post요청으로 게시물을 삭제하기 위한 메소드
    - 비밀번호를 확인하고 일치하면 전달된 `id`에 해당하는 게시물 삭제
    - 비밀번호를 확인하고 일치하면 해당 게시물의 댓글도 삭제
    - 다시 첫화면(홈페이지)으로 돌아가기 위해 `home.html`반환

### 댓글
1. `Comment` Entity : 댓글 객체를 생성하기 위한 `@Entity`
- `id` : PK
- `text` : 댓글 내용
- `password` : 비밀번호
- `notice` : `@ManyToOne`

2. `CommentService` : `Commnet` 객체 가공을 위한 `@Service`
- `create` : 새로운 `Comment`객체를 만들어 속성값 부여
- `read` : 댓글의 `id`값으로 `Comment`반환
- `readAll` : db에 저장된 모든 `Comment`반환
- `readByNoticeId` : `Notice`의 `id`값으로 해당 게시물에 달린 댓글 반환
- `delete` : `Comment`삭제, 이때 비밀번호를 확인하고 같은 경우만 삭제
- `deleteByNoticeId` : 게시물이 삭제될 때 해당 게시물에 달린 댓글을 삭제

3. `CommentController`
- `addComment` : `lead.html`의 Post요청으로 `Comment`를 생성하기 위한 메소드
    - `/{id}/add-comment`에 전달된 파라미터로 `Comment`객체 생성
    - 동일한 `id`의 `read.html`을 반환함으로써 댓글을 추가한 페이지 그대로 반환
- `deleteComment` : `lead.html`의 Post요청으로 `Comment`를 삭제하기 위한 메소드
    - 비밀번호를 확인하고 일치하면 전달된 `id`에 해당하는 `Comment`삭제
    - 동일한 `id`의 `read.html`을 반환함으로써 댓글을 삭제한 페이지 그대로 반환

### template
1. `home.html` : 첫 화면
- 게시물 생성 링크
- 게사판별 링크

2. `board.html` : 게시판별 화면
- 게시판 `type`과 일치하는 게시물들의 제목 보여줌

3. `create.html` : 게시물 생성 페이지
- 제목, 내용, 게시판, 비밀번호 입력

4. `read.html` : 단일 게시물 화면
- 게시물 제목, 내용 확인
- 게시물 삭제 기능
- 댓글 목록 확인
- 새로운 댓글 입력 기능
- 댓글 삭제 기능

## 프로젝트 실행 및 테스트
### 실행
프로젝트 실행 후 <http://localhost:8080/home> 를 통해 실행

### 테스트 완료 내용
- 게시물 생성 가능
- 게시물 생성 시 선택한 게시판 페이지에서 게시물 조회 가능 
- 비밀번호 맞는 경우 게시물 수정 반영
- 비밀번호가 틀린 경우 게시물 수정 안됨
- 단일 게시물 페이지에서 댓글 추가 가능
- 단일 게시물 페이지에서 댓글 조회 가능
- 비밀번호가 맞는 경우 댓글 삭제
- 비밀번호가 틀린 경우 댓글 삭제 안됨
- 게시물 삭제 가능, 게시물 삭제 시 댓글들도 삭제

### 데이터 보존
JPA 설정의 경우 ddl-auto: `create`로 실행 -> `update`로 실행 시 기존 데이터 보존
```
  jpa:
    hibernate:
      ddl-auto: create
    #    show-sql: true
    database-platform: org.hibernate.community.dialect.SQLiteDialect

```

## 프로젝트 마무리
### 새로 배운 점
- `@ManyToOne`을 활용해 테이블간 관계를 Entity의 필드로 표현하기
- Thymeleaf를 활용해 model로 전달받은 attribute 값 활용하기
    - @{/주소/{속성이름}}(속성이름=${속성값})}
- 하나의 탬플릿에 attribute값을 다르게 전달하여 다른 게시판 나타내기 가능
- HTML Form의 Post 요청 처리
    - action을 통해 사용자로부터 정보를 받을 수 있음
### 어려웠던 점 & 개선 필요한 점
- Spring MVC 패턴에 대한 이해가 부족함
- DBMS와 SQL에 대한 이해가 부족함
- 구현은 가능하나 아직 각각의 기능에 대한 이해가 부족함
    - Repository의 역할
    - Dao와 Repository의 차이
    - Dto와 Entity의 차이
- 성능적 개선 필요 
- 각 게시판 `type`에 맞는 `Notice`를 불러오기 위해 모든 `Notice`를 조회하며 그 중 `type`이 일치하는 `Notice`만 리스트에 담아 반환함
    - 성능적으로 더 나은 방법 고민 필요 
- `Notice`에 연결된 `Comment`들을 찾기 위해 모든 `Comment`의 `Notice` Entity를 조회하며 일치하는 `Comment`만 리스트에 담아 반환함
    - 성능적으로 더 나은 방법 고민 필요 
