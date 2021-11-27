## [해, 커리어] 다믐
다믐에서 사용하는 백엔드 개발 코드 보관소입니다.

### 활동 개요
- 요구 사항 분석 및 데이터베이스 설계 [요구사항](https://github.com/wanted-hackareer/backend/blob/main/data/%ED%95%A9%EB%8F%99%20%EC%9E%A5%EB%B3%B4%EA%B8%B0%20%EC%9A%94%EA%B5%AC%EC%82%AC%ED%95%AD.pdf) | [내부 스키마](https://github.com/wanted-hackareer/backend/blob/main/data/%EB%82%B4%EB%B6%80_%EC%8A%A4%ED%82%A4%EB%A7%88.png) | [개념 스키마](https://github.com/wanted-hackareer/backend/blob/main/data/%EA%B0%9C%EB%85%90%20%EC%8A%A4%ED%82%A4%EB%A7%88.png)
- 데이터 플로우 및 ER 다이어그램 제작 [데이터 플로우](https://github.com/wanted-hackareer/backend/tree/main/data/%EB%8D%B0%EC%9D%B4%ED%84%B0%20%ED%94%8C%EB%A1%9C%EC%9A%B0) | [ER 다이어그램](https://github.com/wanted-hackareer/backend/tree/main/data/ER%20%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8)
- 테스트 코드 작성 (domain, service, controller)
- 서비스에 필요한 API 개발
- postman을 사용한 API 기능 테스트 [postman 테스트 자료(json)](https://github.com/wanted-hackareer/backend/blob/main/data/hackareer.postman_collection.json)

### 프로젝트 진행 중 발생한 문제점
#### QueryDsl을 사용하여 검색어와 비슷한 결과를 반환 오류
Qpost.post.title.like(검색어)를 사용하였는데 다른 sql문의 like와는 다르게 QueryDsl에서는 검색어와 일치한 결과만 반환하였습니다. 따라서 contains를 사용하는 것을 문제를 해결하였습니다.

---

#### JWT 토큰 인증 오류 시 에러 반환
jwt 인증 시 OncePerRequestFilter를 사용하여 RuntimeException이 반환되지 않는 문제가 있었습니다. 따라서 다른 error 처리와는 다르게 오류 발생 시 response에 직접 (message, status, code)를 넣어주는 것으로 해결하였습니다.

---

#### 다수의 tag, item 한번에 저장
tag, item은 원래 1번에 하나의 값만 받아와서 저장하는 방식이었지만 item과 tag는 여러 개를 한번에 처리해야한다는 의견이 있어서 requestDto를 List 형식으로 입력 받아 순차적으로 처리하는 것으로 문제를 해결하였습니다.

---

### 남은 해야 할 일
- spring-websocket을 사용하여 게시글마다 채팅방을 하나씩 만들어서 websocket 통신으로 채팅 및 데이터베이스에 저장
- restdocs 사용해여 controller 테스크 코드 작성 및 문서화

### 사용 기술
- spring boot starter (validation, data-jpa, web, security)
- jjwt
- java 8
- lombok
- querydsl

### 데이터베이스
- h2 database

### 테스트
- Junit 4
- spring-restdocs-mockmvc
- spring-boot-starter-test
