# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 추가 기능
### 추가 기능 정리
- 회원가입 api 구현
- 로그인 api 구현 및 쿠키로 로그인 상태 관리
- 사용자 List 전달 api 구현
- JS, CSS forward 기능 구현
- 페이지 redirect api 구현

### 작성 파일
- controller            : 컨트롤러 인터페이스
- AbstractController    : 컨트롤러에서 공통적으로 가진 변수, 매서드, 로그인관리 정의
- CreateUserController  : 회원가입 컨트롤러
- FowardController      : CSS, JS, HTML foward 기능 컨트롤러
- ListUserController    : 사용자 목록 호출 api 컨트롤러
- LoginController       : 로그인 컨트롤러
- HttpRequest           : request 파싱 및 header, body 저장
- HttpResponse          : response 조립 및 DataInputStream에 write
- HttpStatus            : http 상태 enum
- HttpHeader            : request header 파싱 및 저장
- HttpBody              : request body 파싱 및 저장