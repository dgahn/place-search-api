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
GET /api/v1/top-place
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