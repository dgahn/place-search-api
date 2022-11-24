# 장소 검색 서비스

- 카카오 검색 API, 네이버 검색 API를 활용하여 장소를 검색합니다.

## 기능 요구사항

### 장소 검색 API

- 검색결과가 10개 이상이면 각각 5개씩 제한한다.
- 특정 검색 결과가 5개 이하면 최대한 10개에 맞춰 적용한다.
- 결과의 순서는 카카오가 먼저 오도록
- 같은 장소인 경우 제거
- 같은 장소인지 판단하는 기준
    - 업체명 공백 제거
    - 태그 제거 비교
    - 문자열 유사도 비교
    - 장소 위치 비교

### 검색 키워드 목록 API

- 사용자들이 많이 검색한 순서대로, 최대 10개의 키워드 목록을 제공
- 키워드 별로 검색된 횟수도 표기

## API 명세

### 장소 검색 API

```
GET /api/v1/places
```

#### Request Query Param

| Name    |Type| Description     |Required|
|---------|---|-----------------|---|
| query |String| 검색하고 싶은 장소의 키워드 |O|

#### Response Body Param

| Name  |Type| Description                   |Required|
|-------|---|-------------------------------|---|
| title |String| 검색된 장소의 이름                    |O|
| type  |String| 검색된 장소가 검색된 API(KAKAO, NAVER) |O|

### 검색 키워드 목록 API

```
GET /api/v1/places/searched-top
```

#### Request Query Param

| Name | Type | Description                  | Required |
|------|------|------------------------------|----------|
| size | Int  | 결과 키워드 수 1~10 사이의 값, 기본 값 10 | X        |

#### Response Body Param

| Name    | Type   | Description | Required |
|---------|--------|-------------|----------|
| keyword | String | 검색 키워드      | O        |
| rank    | Int    | 순위          | O        |
| count   | Long   | 검색된 횟수      | O        |

## 기술 요구사항

### Java 8 이상 또는 Kotlin 언어로 구현
- kotlin으로 구현

### Spring Boot 사용 
- `spring-boot-starter-web`, `spring-boot-starter-validation`, `spring-boot-starter-data-jpa`

### Gradle 또는 Maven 기반 프로젝트
- gradle 기반으로 프로젝트 구성

### 저장소가 필요할 경우 자유롭게 선택( 예: h2, in-memory 자료구조 등 )
- mysql 선택

### 외부 라이브러리 및 오픈소스 사용 가능
- detekt : 정적 파일 도구로써 kotlin 커뮤니티에서 추천하는 코드 규칙 적용 
- kotlinter : linter 적용 
- kotest-assertions-core : 서드파티용 매처로 infix function 제공
- mockk : kotlin 전용 문법 mock 제공 ex) top-level function, object 등

### cURL 로 테스트하는 방법을 README 파일에 추가 혹은 HTTP Request file ( https://www.jetbrains.com/help/idea/http-client-in-product-code-editor.html#creating-http-request-files ) 을 작성하여 프로젝트에 추가
- 프로젝트에 HTTP Request file 첨부
- `http/scenario.http` 참고

### 동시성 이슈가 발생할 수 있는 부분을 염두에 둔 설계 및 구현 (예시. 키워드 별로 검색된 횟수)
- 동시성 이슈로 인해 업데이트가 원할하게 안될 것을 염려하여 `@Version`을 통해 낙관적 잠금 적용

### 카카오, 네이버 등 검색 API 제공자의 “다양한” 장애 발생 상황에 대한 고려
- 카카오, 네이버 검색시 에러가 발생하면 2번 retry 함
- 재시도해도 에러가 발생하면 emptyList를 반환함
- 카카오 또는 네이버 한쪽만 에러가 발생할 시에 한쪽 결과를 최대 10개까지 반환하도록 로직이 구성되어 있음

### 구글 장소 검색 등 새로운 검색 API 제공자의 추가 시 변경 영역 최소화에 대한 고려
- 구글 장소 검색 추가시 
  - `PlaceSearchClient`에 구글 클라이언트 추가
  - `GooglePlaceResponseDto` 추가 후 `Place`로 변경하는 로직 추가

### 서비스 오류 및 장애 처리 방법에 대한 고려
- 발생할만한 에러에 대해 `ControllerAdvice`를 통해 에러 처리함

### 대용량 트래픽 처리를 위한 반응성(Low Latency), 확장성(Scalability), 가용성(Availability)을 높이기 위한 고려
- `검색 키워드 목록 API`의 경우 특정 시간대에는 랭킹의 변화가 심하지 않을 것을 고려하여 캐쉬 적용
- 구현 편의상 카페인 캐시를 적용

### 기타
- github action을 통한 자동 도커 빌드 추가