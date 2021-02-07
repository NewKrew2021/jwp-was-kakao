# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

##기능 구현 목록

### 요구사항 1 (index.html 파일을 읽어 클라이언트에 응답)

#### 모든 Request Header 출력하기
* [x] BufferedReader를 활용하여 header를 라인 별로 읽기
* [x] logger를 활용해서 http header 전체 출력하기

#### Request Line에서 path 분리하기
* [x] header의 첫 라인에서 요청 URL 추출

#### path에 해당하는 파일 읽어 응답하기
* [x] utils.FileIoUtils를 이용해 classpath에 있는 파일 읽기
* [x] 서버 내 파일이 없으면 404 (Not Found)를 반환

#### Http Request 메세지 저장하기
* [x] Request 전체를 파싱해서 key-value의 map으로 저장

### 요구사항 2 (회원가입 기능 - GET 방식)

#### Request Parameter 추출하기
* [x] 요청 Url의 접근 경로에서 Parameter 분리하기
* [x] 분리한 Parameter 값을 User 클래스에 저장

### 요구사항 3 (회원가입 기능 - POST 방식)

#### Http Method 분기 처리
* [x] Get / Post에 대한 요청 분기 처리

#### Request Body 추출하기
* [x] util.IOUtils를 활용하여 Http Body를 읽기
* [x] Http Body를 해석하여 User 객체 생성

### 요구사항 4 (회원가입 완료 후 index.html로 이동)

#### redirect 사용하여 index.html로 이동
* [x] 회원가입 버튼 클릭 시 index.html로 redirect
* [x] status code 302 헤더 만들기
* [x] status code 302 리턴

### 요구사항 5 (로그인 구현)

#### 로그인 성공 여부 구현
* [x] 로그인이 성공하면 index.html 이동
* [x] 로그인이 실패하면 /user/login_failed.html 이동
* [x] 로그인 시 아이디와 비밀번호가 일치하는지 확인하는 기능

#### 세션 구현
* [x] 로그인 성공 여부에 대한 세션값을 헤더에 설정
* [x] 회원가입 시 User를 DataBase.addUser() 메서드를 활용해 저장

### 요구사항 6 (사용자 목록 구현)

#### 사용자 목록 출력
* [x] /user/list 접속 분기 처리 구현
* [x] 로그인 여부에 따라 사용자 목록 출력 여부 판단
* [x] handlebars.java를 이용하여 동적으로 사용자 목록을 포함한 html을 생성

### 요구사항 7 (Stylesheet 파일 지원 구현)

#### Stylesheet 파일 지원 구현
* [x] 모든 페이지에 Stylesheet 적용

### 세션 구현하기

#### HttpSession 정의 및 구현
* [x] HttpSession 객체 정의
* [x] HttpSession 관리하는 자료 구조 구현
* [x] 각 Controller에 세션 로직 구현

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)