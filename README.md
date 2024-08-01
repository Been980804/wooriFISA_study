# WooriFISA Week4 데일리 미션 : JPA Join 활용

<br/>

<details>
  <summary>목차</summary>  
  
  - [수행 과제](#notebook-수행-과제)
  - [팀원](#raising_hand-팀원)
  - [주제](#memo-주제)
  - [실습환경](rocket-#실습환경)
  - [ER 다이어그램](#floppy_disk-ER-다이어그램)
  - [실행](#globe_with_meridians-실행)
  - [트러블슈팅](#hammer-트러블슈팅)
  - [회고](#thought_balloon-회고)

</details>

## :notebook: 수행 과제
- JPA Join 사용

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
![image](https://github.com/user-attachments/assets/03e67829-ee70-40e2-9eac-0dcd8bdc40d2)



### Member 테이블
### Product 테이블
### Subscribe 테이블

<br/>

## :globe_with_meridians: 실행
```sql
SELECT * FROM MEMBER;
```
![mem](https://github.com/user-attachments/assets/87aec6e7-8f0e-4d67-95e7-aca10278be4b)

<br/>

```sql
SELECT * FROM PRODUCT;
```
![product](https://github.com/user-attachments/assets/8908170f-3806-46c6-9288-daefd56a91bf)

<br/>

```sql
SELECT * FROM SUBSCRIBE;
```

![sub](https://github.com/user-attachments/assets/51f671f3-8013-4ef9-83cb-3c6a7503ed4d)

<br/>

```sql
SELECT s.SUB_ID, m.MEM_NAME, p.PROD_NAME, p.PROD_PRICE
FROM SUBSCRIBE s
JOIN MEMBER m ON s.MEM_ID = m.MEM_ID 
JOIN PRODUCT p ON s.PROD_ID = p.PROD_ID;
```
![result](https://github.com/user-attachments/assets/a950f4c7-74ff-4fea-8bfe-fc19a049144c)

<br/>

## :hammer: 트러블슈팅



<br/>

## :thought_balloon: 회고
### [허예은](https://github.com/yyyeun)
> 
<br/>

### [조성현](https://github.com/cshharry)
> 
<br/>

### [이현빈](https://github.com/been980804)
> Entity객체를 생성하여 클래스간의 Join관계를 생성하면서 OneToOne, ManyToOne, OneToMany 를 고민하며 다시금 테이블간의 관계를 학습할 수 있었습니다.
<br/>

### [이승준](https://github.com/leesj000603)
> JPA를 통해 java 객체와 Oracle의 테이블을 매핑하고, OneToMany,ManyToOne,OneToOne 등의 관계설정을 통해 ORM과 데이터베이스에 대한 이해를 높일 수 있었습니다.
<br/>
