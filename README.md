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

#### form.html 응답하기
* [ ] Request를 해석하여 form.html 응답하기

#### Request Parameter 추출하기
* [ ] 요청 Url의 접근 경로에서 Parameter 분리하기
* [ ] 분리한 Parameter 값을 User 클래스에 저장

#### path에 해당하는 파일 읽어 응답하기
* [ ] utils.FileIoUtils를 이용해 classpath에 있는 파일 읽기

### 요구사항 3 (회원가입 기능 - POST 방식)

#### Http Method 분기 처리
* [ ] Get / Post에 대한 요청 분기 처리

#### Request Body 추출하기
* [ ] util.IOUtils를 활용하여 Http Body를 읽기
* [ ] Http Body를 해석하여 User 객체 생성

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)