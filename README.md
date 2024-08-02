# WooriFISA Week4 데일리 미션 : JPA Join 활용

<details>
  <summary>목차</summary>  
  
  - [수행 과제](#notebook-수행-과제)
  - [팀원](#raising_hand-팀원)
  - [주제](#memo-주제)
  - [실습환경](#rocket-#실습환경)
  - [ER 다이어그램](#floppy_disk-ER-다이어그램)
  - [실행](#globe_with_meridians-실행)
  - [OJT 문제 출제](#sparkles-OJT-문제-출제)
  - [회고](#thought_balloon-회고)

</details>

<br/>

## :notebook: 수행 과제
- JPA Join 사용
- OJT 문제 제출

<br/>

## :raising_hand: 팀원
|<img src="https://github.com/leesj000603.png" width="80">|<img src="https://github.com/been980804.png" width="80">|<img src="https://github.com/cshharry.png" width="80">|<img src="https://github.com/yyyeun.png" width="80">|
|:---:|:---:|:---:|:---:|
|[이승준](https://github.com/leesj000603)|[이현빈](https://github.com/been980804)|[조성현](https://github.com/cshharry)|[허예은](https://github.com/yyyeun)|

<br/>

## :memo: 주제
- 사용자 맞춤형 정기 구독 서비스

<br/>

## :rocket: 실습환경
:green_heart: **JDK 17**
:bookmark: **STS 4** 
:book: **Oracle 11 XE**

<br/>

## :floppy_disk: ER 다이어그램
![erderd](https://github.com/user-attachments/assets/b8b8895d-1b51-468d-b6e3-2386b3cc8390)

### Member 테이블
- 유저, 사업자, 관리자를 모두 포함하는 회원 테이블입니다.
### Product 테이블
- 구독할 상품의 정보에 대한 테이블입니다.
### Subscribe 테이블
- 각 유저가 생성하는 구독 플랜에 대한 테이블입니다.

<br/>

## :globe_with_meridians: 실행
```sql
SELECT * FROM MEMBER;
```
![image](https://github.com/user-attachments/assets/f9eca450-89ec-4d66-9f91-7ed073b69d9e)


<br/>

```sql
SELECT * FROM PRODUCT;
```
![image (6)](https://github.com/user-attachments/assets/ba5b5795-e373-4755-810c-ad9178db3789)

<br/>

```sql
SELECT * FROM SUBSCRIBE;
```
![image (2)](https://github.com/user-attachments/assets/385e8b11-73ee-4c9e-b13c-ecfef5c13b0a)

<br/>

```sql
SELECT s.SUB_ID, m.MEM_NAME, p.PROD_NAME, p.PROD_PRICE
FROM SUBSCRIBE s
JOIN MEMBER m ON s.MEM_ID = m.MEM_ID 
JOIN PRODUCT p ON s.PROD_ID = p.PROD_ID;
```
![image (4)](https://github.com/user-attachments/assets/2332e636-b91e-4270-ad83-f43bf648ad5c)

<br/>

## :sparkles: OJT 문제 출제

### 1. [ Product, Subscribe ] 빈 칸을 채워 각 Entity간의 관계(Join)를 설정하시오. (상단 Table과 ERD를 참고하시오.)

<br/>

**Product**
<p align="center">
  <img width="60%" src="https://github.com/user-attachments/assets/438f89ec-0aaf-4f6d-a94a-282144c83a3d">
</p>

<br/>

**Subscribe**
<p align="center">
  <img width="60%" src="https://github.com/user-attachments/assets/85b13e98-53ef-4aa6-bdaa-a2a9c5e58e88">
</p>

<br/>

### 2. [ RunningTest ] 빈 칸을 채워 코드를 완성하시오.
- **Select** : 첫 번째 구독 플랜의 유저 정보 조회
- **Update** : 첫 번째 구독 플랜의 상품 정보 변경 : 휴지 -> 생수
<p align="center">
  <img width="50%" src="https://github.com/user-attachments/assets/95fffbfe-36a1-47fa-ac9e-d35cad3b44fd">
</p>

<br/>

## :thought_balloon: 회고
### [허예은](https://github.com/yyyeun)
> 직접 Entity와 그 관계를 설계하며 Join에 대한 이해도를 높일 수 있었습니다. OJT 문제를 출제하며 활용했던 Core 개념이 기억에 오래 남을 것 같습니다.
<br/>

### [조성현](https://github.com/cshharry)
> 객체를 통해 간접적으로 DB를 관리해보며 양방향 관계를 설정할 때  두 엔티티의 관계를 올바르게 설정해야 함을 깨달았고, 복잡한 매핑 관계를 더 많이 실습해봐야겠습니다.
<br/>

### [이현빈](https://github.com/been980804)
> Entity객체를 생성하여 클래스간의 Join관계를 생성하면서 OneToOne, ManyToOne, OneToMany 를 고민하며 다시금 테이블간의 관계를 학습할 수 있었습니다.
<br/>

### [이승준](https://github.com/leesj000603)
> JPA를 통해 java 객체와 Oracle의 테이블을 매핑하고, OneToMany,ManyToOne,OneToOne 등의 관계설정을 통해 ORM과 데이터베이스에 대한 이해를 높일 수 있었습니다.
<br/>
