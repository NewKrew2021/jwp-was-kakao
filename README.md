# 웹 애플리케이션 서버

## 목차

1. [미션 소개](#미션-소개)
    1. [1단계](#1단계--HTTP-웹-서버-구현)
    2. [2단계](#2단계--HTTP-웹-서버-리팩토링)
    3. [3단계](#3단계--세션-구현)
2. [진행 방법](#진행-방법)
3. [코드 리뷰 과정](#온라인-코드-리뷰-과정)

---

## 미션 소개

### 1단계 : HTTP 웹 서버 구현

아래의 요구사항을 충족시키는 것을 목표로 한다.

* 요구사항 1 - **index.html**  
<http://localhost:8080/index.html>로 접속했을 때 webapp 디렉토리의 `index.html` 파일을 읽어 클라이언트에 응답한다.
  * HTTP Request Header 예시

  ```HTTP
    GET /index.html HTTP/1.1
    Host: localhost:8080
    Connection: keep-alive
    Accept: */*
  ```

* 요구사항 2 - **회원가입**  
`회원가입` 메뉴를 클릭하면 <http://localhost:8080/user/form.html>으로 이동하면서 회원가입할 수 있다.

* 요구사항 3 - **HTTP Method 변경 (GET -> POST)**  
<http://localhost:8080/user/form.html> 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입이 정상적으로 동작하도록 구현한다.

* 요구사항 4 - **Redirect**  
`회원가입`을 완료하면 `/index.html` 페이지로 이동하고 싶다. 현재는 URL이 `/user/create`로 유지되는 상태로 읽어서 전달할 파일이 없다. 따라서 redirect 방식처럼 회원가입을 완료한 후 `index.html`로 이동해야 한다. 즉, 브라우저의 URL이 `/index.html`로 변경해야 한다.
  * HTTP 응답 헤더의 status code를 200이 아니라 302 code를 사용한다.

* 요구사항 5 - **로그인**  
`로그인` 메뉴를 클릭하면 <http://localhost:8080/user/login.html>으로 이동해 로그인할 수 있다. 로그인이 성공하면 `index.html`, 실패하면 `/user/login_failed.html`로 이동해야 한다. 앞서 회원가입한 사용자로 로그인할 수 있어야 한다. 로그인이 성공하면 **cookie**를 활용해 로그인 상태를 유지할 수 있어야 한다. 로그인이 성공할 경우 요청 header의 Cookie header 값이 `logined=true`, 실패하면 `logined=fase`로 전달되어야 한다.

* 요구사항 6 - **사용자 목록 조회**  
접근하고 있는 사용자가 `로그인 상태`(Cookie 값이 logined=true)일 경우 <http://localhost:8080/user/list>로 접근했을 때 사용자 목록을 출력한다. 만약 로그인하지 않은 상태라면 로그인 페이지`/user/login.html`로 이동한다.

* 요구사항 7 - **Content Type**  
stylesheet 파일을 지원하도록 구현한다.

### 2단계 : HTTP 웹 서버 리팩토링

앞 단계에서 구현한 코드는 WAS 기능, HTTP 요청/응답 처리, 개발자가 구현할 애플리케이션 기능이 혼재되어 있다. 이와 같이 여러가지 역할을 갖는 코드가 혼재되어 있으면 재사용하기 힘들다. **각각의 역할을 분리**해 재사용 가능하도록 개선한다. 즉, WAS 기능, HTTP 요청/응답 처리 기능은 애플리케이션 개발자가 신경쓰지 않아도 재사용이 가능한 구조가 되도록 한다. 소스 코드 리팩토링을 통해 복잡도를 낮춰보자.

### 3단계 : 세션 구현

서블릿에서 지원하는 HttpSession API의 일부를 지원해야 한다. HttpSession API 중 구현할 메소드는 다음과 같다.

* `String : getId()`: 현재 세션에 할당되어 있는 고유한 세션 아이디를 반환
* `void setAttribute(String name, Object value)`: 현재 세션에 value 인자로 전달되는 객체를 name 인자 이름으로 저장
* `Object getAttribute(String name)`: 현재 세션에 name 인자로 저장되어 있는 객체 값을 반환
* `void removeAttribute(String name)`: 현재 세션에 name 인자로 저장되어 있는 객체 값을 삭제
* `void invalidate()`: 현재 세션에 저장되어 있는 모든 값을 삭제

---

## 진행 방법

* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

---

## 온라인 코드 리뷰 과정

* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)
