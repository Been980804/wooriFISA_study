# Docker Image Optimization: Tips & Tricks / Reduce the size of the Docker Image

 Docker 이미지 최적화: 팁과 요령/  이미지 크기 줄이기

<br>

![image](https://github.com/user-attachments/assets/b5a7bea0-d963-427a-9435-1c047a2950f6)

<br>

### 1. 최소 기본 이미지 선택

- 최소한의 기본 이미지로 선택 (Alpine Linux/ Scratch)
- 가볍고 필수 구성요소만 포함하여 이미지 크기와 공격 표면 감소

```jsx
# 비교
TAG                                  SIZE
debian                               124.0MB
alpine                               5.0MB
gcr.io/distroless/static-debian11    2.51MB
```
<br>

### 2. 단일 책임의 원칙

- Docker image에는 단일 책임이 있어야 함
- 각 서비스에 대해 별도의 이미지 사용, Docker Compose/k8s 를 사용하여 구성
<br>

### 3. 멀티 스테이지 빌드 사용

- 단일 Dockerfile에서 여러 From문이 사용 가능해짐
- 최종 이미지에서 빌드 시간 종속성 및 아티펙트를 제거

```jsx
# Build stage
FROM node:14-alpine as build

WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Production stage
FROM node:14-alpine as production

WORKDIR /app
COPY --from=build /app/package*.json ./
RUN npm ci --production
COPY --from=build /app/dist ./dist

CMD ["npm", "start"]
```
<br>

### 4. 레이어 최소화

- 단일 RUN 명령으로 결합하여 Docker 이미지의 레이어 수를 줄임
- 이미지 크기 감소, 빌드 프로세스 속도 ↑

```jsx
RUN apt-get update && \
    apt-get install -y git && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*
    
FROM golang:1.18 as build
...
RUN go mod download
RUN go mod verify
```
<br>

### 5.  **.dockerignor**  사**용**

- **.dockerignor**  파일을 만들어 불필요한 파일 및 디렉터리 제외
- 시간 단축 및 큰 파일이 이미지에 포함 X

```jsx
Ex)

node_modules
Dockerfile*
.git
.github
.gitignore
dist/**
README.md
```
<br>

### 6. 특정 태그 사용

- **latest**  태그 대신 특정 버전 태그를 사용
- 재현성 보장, 예기치 않은 변경 방지

```jsx
FROM nginx:<tag>
```
<br>

### 7. Dockerfile 지침 최적화

- 패키지 설치에 특정 버전 사용
- 종속성 수 최소화
- 불필요한 패키지 제거
<br>

### 8. 아티팩트 압축

- 컴파일된 binary 혹은 정적 파일과 같은 빌드 아티펙트 생성 시 Docker 이미지에 복사하기 전에 압축
- 이미지 크기 감소, 빌드 프로세스 ↑
<br>

### 9. 이미지 레이어 검사

- **docker history / docker inspect**  와 같은 도구를 사용하여 이미지 레이어 분석 및 최적화 기회 식별
- Dockerfile에서 불필요한 파일 및 명령 제거
<br>

### 10. Docker 이미지 정리 사용

- **docker system prune**  명령 사용하여 사용하지 않은 Docker 이미지, 컨테이너, 볼륨 및 네트워크 정리
- 디스크 공간 확보, 성능 향상
<br>

### 11. 캐싱 구현

- Dockerfile을 구조화하여 Docker의 빌드 캐시를 활용
- 자주 변경되는 지침을 Dockerfile의 끝에 배치
<br>

### 12. 보안 스캔

- Docker 보안 스캔 도구를 사용하여 Docker 이미지의 보안 취약성을 식별 및 수정

- 정기적으로 이미지 스캔하여 취약점 확인 및 보안 패치 적용
<br>

### 13. 더 작은 대안 사용

- 도구 및 라이브러리에 대해 더 작은 대안 사용
- 본격적인 배포 대신 BusyBox / Microcontainers 사용
<br>

### 14. 설치 후 정리

- 패키지 설치 과정에 생성된 임시 파일 및 캐시 제거
<br>

### 15. Docker Squash 사용

- 계층을 병합하여 Docker 이미지의 크기 감소
- 단, 빌드 시간 ↑, 캐시 가능성 ↓ 할 수 있음

---
<details>
<summary>실습</summary>
<div markdown="1">
 
```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int num = Integer.parseInt(br.readLine());
            // 구구단
            for(int i = 1; i <= 9; i++){
                System.out.println(num + " * "+  i + " = " + num*i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

```
  <br>
  <ul>
   <li>Alpine 이미지 사용</li>
   
   ![image](https://github.com/user-attachments/assets/f795c814-53de-431d-bd3a-e61522e116b9)

   <br>
   <li>Multi-Stage 빌드</li>

   ![image](https://github.com/user-attachments/assets/8d87c937-c26a-4690-9a5d-d384d41afa14)

   <br>
   <li>.dockerignore 사용</li>

   ![image](https://github.com/user-attachments/assets/16d236ad-f811-4708-ae8b-3b6b09bf6f0c)

   <br>
   <li>이미지 빌드 및 설정</li>

   ![image](https://github.com/user-attachments/assets/a5ad75bc-1a62-4086-9ba2-f89641d9b48e)

   <br>
   <li>docker 로그인</li>
   
   ![image](https://github.com/user-attachments/assets/ebc0913c-1a0c-4cf7-b749-89c8fb8c07e9)

   <br>
   <li>이미지 푸시</li>

   ![image](https://github.com/user-attachments/assets/7a3fc8c7-e5af-4f65-9d65-7a50b95373b6)
  </ul>

 </div>
</details>
