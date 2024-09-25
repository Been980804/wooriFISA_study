# Trivy를 사용한 컨테이너 이미지 분석

## Trivy란?

> - Aqua Security에서 개발한 오픈 소스 도구
> - 컨테이너 이미지에서 취약점을 식별하고 관리하는데 사용됨
>
> 기능
> 1. 간편한 설치, 사용 : 간단한 명령어를 통해 쉽게 설치하고 실행 가능,
> Docker나 K8S와 같은 환경에서도 잘 통합
> 
> 2. 취약점 스캔 및 보고 : 특정 컨테이너 이미지에서 발견된 모든 취약점을 신속하게 검색 및 보고 가능, 개발자나 운영팀이 이미지를 사용하기 전에 신속하게 취약점 확인이 가능
> 
> 3. CVE 데이터베이스와의 통합 : CVE 데이터베이스와 통합되며, 알려진 취약점에 대한 최신 정보 제공, 신뢰할 수 있는 데이터를 기반으로 취약점 관리가 가능
>
> ※ CVE → 공개적으로 알려진 컴퓨터 보안 취약점 리스트
>
 
<br>

## Trivy 설치

https://aquasecurity.github.io/trivy/v0.19.2/getting-started/installation/

```docker
# Ubuntu(myserver02) -> Docker
# 1. Docker 설치
# apt 인덱스 업데이트
sudo apt-get update
sudo apt-get install ca-certificates curl gnupg lsb-release

# 2. Docker 공식 GPG 키 추가
sudo mkdir -m 0755 -p /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg

# 3. Docker 저장소를 APT 소스에 추가
echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# 4. APT 패키지 캐시 업데이트
sudo apt-get update

# 6. Docker 서비스 상태 확인
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

# 7. 사용자 권한 설정
sudo usermod -aG docker $USER # docker 명령어 사용시 sudo 권한 부여하는 설정(재부팅 필수)
newgrp docker    # 설정한 그룹 즉각 인식하는 명령어, 생략시 재부팅 후에만 group 적용
groups
tail /etc/group

-------------------------------------------------------------------------

# 2. Trivy 설치
docker pull aquasec/trivy:0.19.2
```

![image](https://github.com/user-attachments/assets/f69a04e4-7cb2-4208-9780-d33fb87a63df)

<br>

## Trivy로 취약성 검사 실행

```docker
# Ex
docker run --rm -v /var/run/docker.sock:/var/run/docker.sock -v /path/to/cache:/root/.cache aquasec/trivy image --format table nginx:latest
```

## 스크립트 이미지화

- 스크립트 생성

```java
public class Trivy{
    public static void main(String[] args){
        System.out.println("trivy 실습");
    }
}
```

- Dockerfile 설정

```docker
FROM openjdk:17
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
RUN javac Trivy.java
CMD ["java", "Trivy"]
```

- 이미지화

```
# . : 해당 디렉토리 전부
docker build -t been980804/tirvy_mission:1.0 .
```

![image](https://github.com/user-attachments/assets/7caeeda7-0b77-4d6d-918b-e44afa4d133b)

<br>

## Crontab 설정

- trivy_json.sh

```
#!/bin/bash

# been980804로 시작하는 이미지 찾기
IMAGES=$(docker images --format "{{.Repository}}:{{.Tag}}" | grep "been980804")

# 오늘 날짜
DATE=$(date +%Y-%m-%d)

# 저장경로 (호스트 경로)
DIR="/home/username/trivy/trivy_result"

# 디렉토리 없을시 생성
mkdir -p ${DIR}
echo "저장 경로: ${DIR}"

for IMAGE in $IMAGES
do
    # 이미지명에서 레포지토리 이름과 태그 추출
    REPO_NAME=$(echo $IMAGE | cut -d':' -f1)
    REPO_BASE=$(basename $REPO_NAME)  # / 뒤에 있는 부분만 추출 (trivy_mission)

    TAG_NAME=$(echo $IMAGE | cut -d':' -f2)

    # 파일명 설정 (result_{레포지토리명}_{날짜}.json 형식)
    FILENAME="result_${REPO_BASE}_${DATE}.json"

    echo "저장될 파일명: ${FILENAME}"

    # 트리비 실행
    docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \
                      -v ${DIR}:/output \
                      aquasec/trivy image --format json \
                      -o /output/${FILENAME} ${IMAGE}

    # 결과 확인
    if [ -f "${DIR}/${FILENAME}" ]; then
        echo "파일 생성 성공: ${DIR}/${FILENAME}"
    else
        echo "파일 생성 실패: ${DIR}/${FILENAME}"
    fi

done
```

- 스크립트에 실행 권한

```
chmod +x /home/username/crontab/trivy_scan.sh
```

- Crontab 설정

```
crontab -e

0 0 * * * /home/username/crontab/trivy_scan.sh

```

<br>

## 최종 결과

![image](https://github.com/user-attachments/assets/8c3f126e-3535-4e37-bb53-c7419740d269)

<br>

## 트러블 슈팅

스크립트 이미지화 과정에서 

![image](https://github.com/user-attachments/assets/fa9c9dec-a625-481e-b347-319c8e2454f5)


```
 IMAGES=$(docker images --format "{{.Repository}}:{{.Tag}}" | grep "been980804")
 
 for IMAGE in $IMAGES
 do
 # 이미지명에서 레포지토리 이름과 태그 추출
    REPO_NAME=$(echo $IMAGE | cut -d':' -f1)
    
    TAG_NAME=$(echo $IMAGE | cut -d':' -f2)

    # 파일명 설정 (result_{레포지토리명}_{날짜}.json 형식)
    FILENAME="result_${REPO_NAME}_${DATE}.json"

    echo "저장될 파일명: ${FILENAME}"

    # 트리비 실행
    docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \
                      -v ${DIR}:/output \
                      aquasec/trivy image --format json \
                      -o /output/${FILENAME} ${IMAGE}
   done
```

repository명이 been980804/trivy_mission 이라 ‘/’ 때문에 docker가 경로를 읽을 때 ‘/’를 디렉토리로 판단

### 해결방법

```

REPO_BASE=$(basename $REPO_NAME)  # / 뒤에 있는 부분만 추출 (trivy_mission)

# 파일명 설정 (result_{레포지토리명}_{날짜}.json 형식)
FILENAME="result_${REPO_BASE}_${DATE}.json"
```

파일명 설정을 been980804/trivy_mission 에서 trivy_mission만 사용하게 수정
