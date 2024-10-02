## SpringApp K8S에 배포 후 LoadBalancer 확인하기

<br>

### 1. Jenkins와 github 연동

```bash
# Jenkins 컨테이너화
docker run --name myjenkins2 --privileged -p 8090:8080 
**-v $(pwd)/appjardir:/var/jenkins_home/appjar** jenkins/jenkins:lts-jdk17
```

<br>

### 2. Jenkins로 jar파일 생성

```bash
pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/Been980804/jenkins_practice'
            }
        }
          
        stage('Build') {
            steps {
                dir('./2024_10_02_SpringApp') {                   
                    sh 'chmod +x gradlew'                    
                    sh './gradlew clean build -x test'
                    sh 'echo $WORKSPACE'   
                }
            }
        }
        
        stage('Copy jar') { 
            steps {
                script {
                    def jarFile = '2024_10_02_SpringApp/build/libs/2024_10_02_SpringApp-0.0.1-SNAPSHOT.jar'                   
                    sh "cp ${jarFile} /var/jenkins_home/appjar/"
                }
            }
        }
    }
}
```

<br>

### 3.  jar파일 image화

- DockerFile

```bash
# 1. 사용할 베이스 이미지 설정 (Java 17 사용)
FROM openjdk:17-jdk-slim

# 2. JAR 파일을 컨테이너로 복사
ARG JAR_FILE=2024_10_02_SpringApp-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# 3. JAR 파일을 실행하는 명령어 지정
ENTRYPOINT ["java", "-jar", "/app.jar"]

```

- .dockerignore

```bash
# test로 만든 yml파일 build에 포함 X
*.yml
```

- build

```bash
# step08k8s 폴더 내에서
cd step08k8s

docker build -t been980804/springapp:1.0 .
```

<br>

### 4. Docker Hub에 push

```bash
docker push been980804/springapp:1.0
```

<br>

### 5. Minikube 시작

```bash
minikube start

# 이미지로 실행
**kubectl create deployment springapp --image=been980804/springapp:1.0 
--replicas=3

# LoadBalncer service
kubectl expose deployment springapp--type=LoadBalancer --port=80 
--target-port=8999**

minikube addons list

minikube addons enable dashboard  
minikube addons enable metrics-server

# dashboard 실행
minikube dashboard

# service 확인
kubectl get services

# tunnel 열어주기 
minikube tunnel

# service 재확인
kubectl get services

#해당 ip 포트포워딩
```

- 서비스 확인
- 
    ![image](https://github.com/user-attachments/assets/8a026c8e-1ab0-48f0-aec3-5fb32f1cdce9)

- 포트 포워딩
- 
    ![image](https://github.com/user-attachments/assets/abc57f71-f3c9-440a-aac0-7e50a97f95c3)

<br>

### 6. Window에서 접속

![image](https://github.com/user-attachments/assets/b31aa863-7463-4472-ad0e-94463b3b3daa)

<br>

### 7. Dashboard 확인

![image](https://github.com/user-attachments/assets/427ed05a-ba4f-446f-a4f8-3f2f48b891c3)

![image](https://github.com/user-attachments/assets/0f8f8210-4655-4488-acec-65f51c8583c8)

![image](https://github.com/user-attachments/assets/5b054bfd-d77f-4beb-87e9-fe1cc12df616)

- 다른 파드에 로그가 찍힌것을 확인 가능
