# CGV 벤치마킹 개인 프로젝트
## Description
**기존 CGV 홈페이지 벤치마킹하여 구현**
- **개발 기간 : 2023/04/13 ~ 2023/05/14**
- **참여 인원 : 개인**
- **사용 기술**
    - **Thymeleaf, CSS, JS, JQuery**
    - **JAVA, Springboot, JDBCTEMPLATE**
    - **Ajax, Git, MVC Pattern**
    - **MYSQL**
## 개발 내용 요약
- **메인 페이지 페이지**
    - **AJAX를 활용하여 영화 리스트, 공지사항 리스트 비동기 처리**   
      HEADER  
      ![image](https://github.com/GOBUMGYU/MY_CGV/assets/106207558/2bf29b7b-369b-4a6c-a575-2fc783abcb6f)
      INDEX  
      ![image](https://github.com/GOBUMGYU/MY_CGV/assets/106207558/d05ed4ef-322c-49d7-8dd2-c01beb35b402)
      ![image](https://github.com/GOBUMGYU/MY_CGV/assets/106207558/e1d34cb7-b515-4345-87c2-66a81683ef7b)
      FOOTER  
      ![image](https://github.com/GOBUMGYU/MY_CGV/assets/106207558/af020abd-9092-44d0-915c-a42e8d4e7cb9)

- **회원가입 페이지**
    - **AJAX를 활용하여 아이디 중복 활인 절차**
    - **다음 우편 주소 API 사용**
    - **JDBCTEMPLATE를 활용하여 회원 데이터 저장**
      ![image](https://github.com/GOBUMGYU/MY_CGV/assets/106207558/ac21ca56-23b5-42ed-bf65-f579be730861)
      ![image](https://github.com/GOBUMGYU/MY_CGV/assets/106207558/0defdc54-604d-4804-9f15-c4fcc58a99af)
- **로그인 페이지**
    - **JDBCTEMPLATE를 통해 데이터 대조**
    - **로그인 성공 시, Session에 회원 ID추가, 헤더 메뉴에 로그인 대신 로그아웃 추가**
    - **로그인, 로그아웃 시 메시지 출력**
      ![image](https://github.com/GOBUMGYU/MY_CGV/assets/106207558/8ce54b4f-b958-4bc3-87f1-45505290f585)
- **공지사항, 게시판 페이지**
    - **공지사항, 게시판 리스트 노출 및 페이징 처리**
    - **게시판 글쓰기, 수정하기, 삭제하기**
    - **첨부파일의 경우 MultiFile 클래스를 활용하여 추가, 수정, 삭제**  
      ![image](https://github.com/GOBUMGYU/MY_CGV/assets/106207558/859807ab-b303-4f12-95fe-58c6dc2b71c6)
      ![image](https://github.com/GOBUMGYU/MY_CGV/assets/106207558/804f82d1-13e4-4fe5-ab7a-ab77690e9707)
      ![image](https://github.com/GOBUMGYU/MY_CGV/assets/106207558/8a38c6c6-73ba-4cd2-ac1d-25559c20c98f)
      ![image](https://github.com/GOBUMGYU/MY_CGV/assets/106207558/053c46d2-606f-407a-b74b-84409c046ce4)
- **관리자 페이지 : interceptor를 활용해 Session에 담긴 회원 ID가 admin인 경우 헤더에 admin메뉴 추가**
  ![image](https://github.com/GOBUMGYU/MY_CGV/assets/106207558/7ca27f1b-f8ad-41d0-9995-3ad0456cede9)
    - **공지사항 관리**  
      ![image](https://github.com/GOBUMGYU/MY_CGV/assets/106207558/0d70e5e4-2ff4-4e32-bc7f-58d9761e2328)
        - **공지사항의 상세보기**
        - **공지사항 글쓰기, 수정, 삭제**
        - **첨부파일의 경우 MultipartFile 클래스를 활용하여 추가, 수정, 삭제**
    - **회원관리**
      ![image](https://github.com/GOBUMGYU/MY_CGV/assets/106207558/6f1e87d7-bb67-4ee7-a5fe-d50d90224b49)
      ![image](https://github.com/GOBUMGYU/MY_CGV/assets/106207558/d69b2906-22bb-4c24-9167-69cfc3216482)
        - 회원 정보 상세보기
    - **영화 등록 관리**
      ![image](https://github.com/GOBUMGYU/MY_CGV/assets/106207558/40e72bd8-a8e8-40da-a30d-ca9808423125)
      ![image](https://github.com/GOBUMGYU/MY_CGV/assets/106207558/da8344e6-3264-4900-8f24-fdc44db50631)
      ![image](https://github.com/GOBUMGYU/MY_CGV/assets/106207558/6e347982-1201-492b-b955-db49ed2c0ecc)
        - **영화 리스트 추가, 상세보기**
        - **첨부 파일의 경우 MultipartFile 클래스를 활용하여 추가, 수정, 삭제**
- **MYCGV 페이지**
  ![image](https://github.com/GOBUMGYU/MY_CGV/assets/106207558/02972378-1129-4e4d-be69-8b752b790483)
  ![image](https://github.com/GOBUMGYU/MY_CGV/assets/106207558/7291a674-9632-45e0-8a83-c4fe358afa74)
    - **예매확인, 개인정보 수정을 위한 페이지 (예매, 개인 정보 수정 기능은 미구현)**
    - **interceptor 를 활용해 회원 아이디가 Session에 담겨 있지 않으면 에러메시지 출력**
- **보안 요소**
    - **단순 URL의 변경을 통해 인가되지 않은 사용자가 잘못된 경로로 이동하는 것을 Session을 통해 활용하여 방지**

## ERD DIAGRAM
![image](https://github.com/GOBUMGYU/MY_CGV/assets/106207558/1d07b655-bffb-49b2-b97e-62fe18b7829c)

