# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## Step 1
### 요구사항 1
* http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
* 모든 Request Header 출력하기
* Request Line에서 path 분리하기
* path에 해당하는 파일 읽어 응답하기

### 요구사항 2
* “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다.
* Request Parameter 추출하기

### 요구사항 3
* http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.
* Request Body의 값 추출하기

### 요구사항 4
* “회원가입”을 완료하면 /index.html 페이지로 이동하고 싶다.
* 회원가입을 완료한 후 “index.html”로 이동해야 한다.

### 요구사항 5
* “로그인” 메뉴를 클릭하면 http://localhost:8080/user/login.html 으로 이동해 로그인할 수 있다. 로그인이 성공하면 index.html로 이동하고, 로그인이 실패하면 /user/login_failed.html로 이동해야 한다
* 회원가입한 사용자로 로그인할 수 있어야 한다. 로그인이 성공하면 cookie를 활용해 로그인 상태를 유지할 수 있어야 한다.

### 요구사항 6
* 접근하고 있는 사용자가 “로그인” 상태일 경우(Cookie 값이 logined=true) 경우 http://localhost:8080/user/list 로 접근했을 때 사용자 목록을 출력한다. 
* 만약 로그인하지 않은 상태라면 로그인 페이지(login.html)로 이동한다.

### 요구사항 7
* Stylesheet 파일을 지원하도록 구현하도록 한다.

## Step2 - HTTP 웹 서버 리팩토링
### 요구사항 : HTTP 요청/응답 처리 기능
* HTTP 요청 Header/Body 처리, 응답 Header/Body 처리만을 담당하는 역할을 분리해 재사용 가능하도록 한다

### 코드 리팩토링 요구사항
* HTTP 웹 서버를 구현하고 보니 소스 코드의 복잡도가 많이 증가했다. 소스 코드 리팩토링을 통해 복잡도를 낮춰보자.
* Bad Smell을 찾는 것은 쉽지 않은 작업이다.
* 리팩토링할 부분을 찾기 힘든 사람은 다음으로 제시하는 1단계 힌트를 참고해 리팩토링을 진행해 볼 것을 추천한다.
* 만약 혼자 힘으로 리팩토링할 부분을 찾은 사람은 먼저 도움 없이 리팩토링을 진행한다.
  


