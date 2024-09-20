# wooriFisa-miniProject

## 도서관 관리 프로그램

### 1️⃣ 개요

 사용자와 관리자가 도서를 대여 / 반납할 수 있고, 관리자는 도서를 등록 / 수정 / 삭제할 수 있다. 

### 2️⃣ 사용자 기능
- 회원가입 / 로그인 / 로그아웃
- 모든 도서 검색
- 도서 대여 / 반납
- 대여 중인 도서 보기

### 3️⃣ 관리자 기능
- 도서 등록 / 수정 / 삭제
  

![image](https://github.com/user-attachments/assets/e9d3be13-a146-4d1a-a09a-ff36acaf3fa2)|![image](https://github.com/user-attachments/assets/01bffae0-eb63-498a-92fc-907d5267b096)
--- | --- |

### 4️⃣ 클래스 구조
    📦src
    ┣ 📂controller
    ┃ ┣ 📜ManageController.java
    ┃ ┗ 📜UserController.java
    ┣ 📂model
    ┃ ┣ 📂domain
    ┃ ┃ ┣ 📜BookDTO.java
    ┃ ┃ ┗ 📜UserDTO.java
    ┃ ┣ 📜BookDAO.java
    ┃ ┣ 📜ManageDAO.java
    ┃ ┗ 📜UserDAO.java
    ┣ 📂service
    ┃ ┣ 📜ManageService.java
    ┃ ┗ 📜UserService.java
    ┗ 📂view
    ┃ ┗ 📜StartView.java
