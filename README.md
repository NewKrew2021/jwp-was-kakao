# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

# 요구사항 목록
## 1단계
- [x] /index.html로 접속하면 webapp 디렉터리의 index.html 파일을 읽어서 클라이언트에게 응답
- [x] 회원가입 메뉴를 클릭하면 /user/form.html로 이동 후 회원가입
- [x] /user/form.html 파일의 form 태그 method를 post로 수정
- [x] 회원가입을 완료하면 /index.html 이동
- [x] 로그인 메뉴 클릭 시 /user/login.html로 이동
    - [x] 성공하면 /index.html로 이동
    - [x] 실패하면 /user/login_failed.html로 이동
- [x] 로그인 상태일 경우 /user/list에 접근 시 사용자 목록 출력
    - [x] 로그인하지 않은 상태면 /user/login.html
- [x] stylesheet 파일 지원
