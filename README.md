<p align="center"><img src="https://github.com/ITE-2023/BookReport/assets/73464584/471f5f08-e15e-4cdb-a393-a5abc1562f27" width="50%" height="50%"></p>

# ❏ 프로젝트 개요

`Emotion Story` 는 도서의 상세 정보를 제공하며, 독서 기록과 독후감을 쉽게 기록할 수 있고, 독후감에 대한 감정 분석을 통해 음악을 추천해주는 웹 사이트이다.

- 도서 검색 후 도서 상세 정보 확인
- 독서에 대한 기록을 내 서재에 저장
- 기록된 독후감에 대한 감정 분석
- 독후감과 음악 가사 간의 유사도 측정
- 유사도가 높은 음악 추천

<br>

## ❍ 프로젝트의 필요성 및 차별성

독서량이 감소하고 있는 심각성을 개선하고자, 사용자들이 읽은 책에 대한 소중한 경험을 기록하고 공유할 수 있도록 프로젝트를 진행하고자 하였다.

또한, 사용자의 독후감과 분위기가 어울리는 `음악을 추천`하여 독서 경험을 더욱 풍부하고 기억에 남도록 도움을 주고자 하였다.

따라서, 도서에 대한 독자들의 감정 정보의 통계적 수치가 나타나는 `감정 그래프` 가 존재하며, 이를 통해 해당 도서에 대한 감정을 미리 체험해볼 수 있으며, 자신과 맞는 책을 효과적으로 고를 수 있다는 장점이 있다.

<br>

## ❍ 시연 영상

<p align="center">
  <a href="https://youtu.be/7xnoZcg-UXM">
    <img src="http://img.youtube.com/vi/7xnoZcg-UXM/0.jpg" alt="Video Label" width="50%" height="50%">
  </a>
</p>

<br>

# ❏ 프로젝트 개발 환경

| 구분              | 개발 환경 | 상세 정보                                                                                                                        |
|-----------------| --- |------------------------------------------------------------------------------------------------------------------------------|
| 프론트엔드(Front-end) | ReactJs | - Node.js : 18.13.0 <br> - Npm : 8.19.3 <br> - React : 18.2 <br> - IDE : Visual Studio Code                                  |
| 백엔드(Back-end) | Spring Boot | - Java : 17.0.6 <br> - Project SDK : 17 <br> - Spring Boot : 3.0.5 <br> - Gradle : 7.6.1 <br> - IDE : Intellij IDEA 2022.2.3 |
|  | Flask | - Python : 3.7.16 <br> - Flask : 2.2.5 <br> - IDE : Pycharm 2023.2.2                                                              |
| DBMS | MariaDB | - MariaDB : 10.4.25                                                                                                          |

<br> 

## ❍ 개체 관계 모델(ERD)

<p align="center">
  <img src="https://github.com/ITE-2023/BookReport/assets/73464584/073a9f4b-298f-42b7-b679-25cd19814da0" alt="image">
</p>

<br>

# ❏ 프로젝트 흐름도 및 동작 과정

## ❍ 사용자 흐름도

<p align="center">
  <img src="https://github.com/ITE-2023/BookReport/assets/73464584/1a425a0b-f9aa-449b-bea9-6324a59c9570" alt="image">
</p>

<br>

## ❍ 주요 기능 동작 과정

<p align="center">
  <img src="https://github.com/ITE-2023/BookReport/assets/73464584/cf68d104-340a-4a9f-8117-13875883289f" alt="image">
</p>

<br>

1. 회원 가입 및 로그인
    - `Spring Security`와 JWT 활용

2. 도서 검색
    - `네이버 오픈 API` 활용
    - 도서 제목을 통한 검색
    - 도서의 상세 정보 확인 (도서 정보, 독후감 목록)
    - 도서 목록 페이징 처리

3. 내 서재 목록에 도서 추가
    - 내 서재에 추가, 수정, 삭제
    - 도서의 상태 입력 (읽은 책, 읽는 중인 책, 읽고 싶은 책)
    - 도서의 상태의 따른 상세 정보 입력
    - 내 서재 목록 페이징 처리

4. 독후감 작성
    - 독후감 추가, 수정

5. 음악 추천
    - `Melon` 크롤링을 통해 감정별 데이터 구성
    - `KOBERT`, 감정 분석 모델 추가 학습 진행
    - 모델을 통해 독후감 내용을 감정 분석 (행복, 분노, 슬픔, 놀람, 공포)
    - `OpenAI`, 감정 분석 결과에 해당하는 음악 가사 데이터와 유사도 측정
    - 유사도 높은 음악 추천 결과 도출

<br>

# ❏ 감정 분석 및 유사도 측정

## ❍ 감정 분석

SKT Brain에서 개발한 KOBERT를 기반으로 감정 분석을 위한 추가 학습 단계를 진행하였다.

1. AIHUB에서 제공하는 ‘감성 대화 말뭉치’라는 데이터 세트를 이용하여 전처리 작업을 진행
2. 준비된 데이터 세트를 이용하여 KOBERT에 대한 추가 학습(Fine-tuning) 진행

<p align="center">
  <img src="https://github.com/ITE-2023/BookReport/assets/73464584/19893417-253e-4433-adff-212caa438cd1" alt="image"  width="60%" height="60%">
</p>


해당 모델은 학습한 내용을 기반으로 독후감에 대한 감정 분석을 진행하여 결과를 도출한다.

분석된 감정 정보는 음악 추천 기능에 활용되게 되며, 사용자가 직접 작성한 수많은 문장은 다양한 감정 형태와 패턴을 지니고 있으므로, 학습에 활용한다면, 모델 성능이 향상될 것임을 기대해볼 수 있다.

<br>

## ❍ 유사도 측정

<p align="center">
  <img src="https://github.com/ITE-2023/BookReport/assets/73464584/b5458ee9-1f40-44c0-ab9c-07054a3eb289" alt="image"  width="60%" height="60%">
</p>

OpenAI는 Text String을 입력으로 받고 Embedding Vector를 출력으로 하는 Text Embedding Model을 제공한다.

텍스트 검색, 코드 검색, 문장 유사성에 있어서 기존 모델보다 우수한 모델인 ‘text-embedding-ada-002’를 사용하여 독후감과 음악 가사를 임베딩하였다.

코사인 유사도를 측정하기 위해 cosine_similarity() API를 제공하는 사이킷런(Sklearn)을 사용하였으며, 최종적으로 가장 유사도 값이 1에 가까운 결과를 도출하여 음악을 추천한다.

<br>

# ❏ API 명세

- 회원(Member) API

| HTTP Method | API | 설명 |
| --- | --- | --- |
| POST | /member/join | 회원 가입 |
| POST | /member/login | 로그인 |
| POST | /member/reissue | Access Token 재발행 |

- 도서(Book) API

| HTTP Method | API | 설명 |
| --- | --- | --- |
| POST | /book/create | 도서 추가 |
| PATCH | /book/update/{id} | 도서 정보 수정 |
| DELETE | /book/delete/{id} | 도서 삭제 |
| GET | /book/search?query={keyword} | 도서 목록 검색 |
| GET | /book/detail/{isbn} | 도서 상세 정보 검색 |

- 내 서재(MyBook) API

| HTTP Method | API | 설명 |
| --- | --- | --- |
| POST | /myBook/create | 내 서재에 도서 추가 |
| PATCH | /myBook/update/{id} | 추가된 도서 상태 정보 수정 |
| DELETE | /myBook/delete/{id} | 내 서재에 도서 삭제 |
| GET | /myBooks | 내 서재 목록 조회 |
| GET | /myBook/detail/{id} | 추가된 도서 상태 정보 단 건 조회 |
| GET | /myBook/check/{id} | 이미 추가된 도서인지 여부 확인 |

- 독후감(Report) API

| HTTP Method | API | 설명 |
| --- | --- | --- |
| POST | /report/create/{myBookId} | 독후감 등록 |
| PATCH | /report/update/{id} | 독후감 수정 |
| GET | /report/detail?myBook={myBookId} | 내 서재 속 독후감 조회 |
| GET | /report/detail/{id} | 독후감 단 건 조회 |
| GET | /reports/{isbn} | 책 별 독후감 목록 조회 |

- 감정(Emotion) API

| HTTP Method | API | 설명 |
| --- | --- | --- |
| GET | /emotion/{isbn} | 해당 도서의 감정 통계 조회 |
| POST | /emotion | 독후감 감정 분석(Flask) |

- 음악(Music) API

| HTTP Method | API | 설명 |
| --- | --- | --- |
| GET | /music/recommend/{id} | 독후감 별 추천 음악 조회 |
| GET | /music | 문서 유사도가 높은 음악 추천(Flask) |