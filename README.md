# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)


## 기능 요구사항
1. http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
2. “회원가입” 메뉴를 클릭하면 form.html 으로 이동하여 회원가입을 할 수 있다.
3. form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 한다.
4. “회원가입”을 완료하면 index.html 페이지로 이동한다.
5. “로그인” 메뉴를 클릭하면 login.html 으로 이동해 로그인할 수 있다. 로그인이 성공하면 index.html로 이동, 로그인이 실패하면 login_failed.html로 이동한다.
6. 접근하고 있는 사용자가 http://localhost:8080/user/list 로 접근했을 때 로그인된 상태라면 사용자 목록을 출력, 로그인하지 않은 상태라면 login.html로 이동한다.
7. Stylesheet 파일을 지원하도록 한다.

## todo
* [x] HttpRequest 구현
    * [x] RequestMethod 구현 
    * [x] HttpRequestHeader 구현 
    * [x] HttpRequestHeaders 구현
    * [x] HttpRequestParser 구현
  
* [x] Controller 구현
    * [x] Dispatcher 구현
    * [x] DispatchInfo 구현
    * [x] TemplateController 구현
    * [x] UserController 구현
    * [x] StaticController 구현