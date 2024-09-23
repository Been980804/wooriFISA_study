# 🦁 리눅스의 평균 부하에 대한 심층적 이해

## 🐴 시스템 부하 파악

```
top

uptime
```
<br>

![img-1](https://github.com/user-attachments/assets/85f92840-7179-4d31-adf0-63eca448cb26)
<br>
- 12:17:29 → 현재 시간
- up 3:11 → 시스템 가동 시간
- 2 users → 사용자 수
- 0.14, 0.08, 0.05 → 1분, 5분, 15분 동안의 평균 부하
    <br>
    > 평균 부하란?
    
    단위 시간 동안 실행 가능 또는 중단 불가능한 상태의 프로세스의 평균 수 (CPU 사용률과는 직접적인 관련 없음)
    
    Ex) 평균 부하 = 2
    1. CPU가 2개
    → 모든 CPU가 완전히 사용 중
    
    2. CPU가 4개
    → 4개의 CPU가 50%씩 사용 중
    
    3. CPU가 1개
    → 1개의 CPU에서 두개의 프로세스들이 경쟁 중
    > 

- 1분, 5분, 15분의 값이 비슷함
    
    → 시스템 부하가 안정적임
    
- 1분 값이 15분 값보다 훨씬 낮음
    
    → 지난 15분 동안 상당한 부하가 있었지만, 마지막에 부하가 감소함
    
- 1분 값이 15분 값보다 훨씬 높음
    
    → 마지막 1분의 부하가 증가했음
    
    → 시스템에 과부하가 발생했음을 의미
    
    → 문제의 원인 분석 및 최적화 필요
    
<br>

## 🐼 사례

### 🦊 사전준비

- 컴퓨터 구성 : CPU 2개, 8GB RAM
- 사전 설치 패키지 : stress, sysstat
    - stress : 비정상적인 프로세스로 인해 평균 부하가 증가하는 시나리오를 시뮬레이션하기 위해 사용되는 Linux 시스템 stress 테스트 도구
    - sysstat : 시스템 성능을 모니터링하고 분석하기 위해 사용되는 Linux 성능 도구 포함 (mpstat, pidstat 명령어 사용)
        - mpstat : 각 CPU에 대한 실시간 성능 메트릭과 모든 CPU에 대한 평균 메트릭 확인
        - pidstat : 프로세스의 CPU, 메모리 I/O 및 컨텍스트 스위치에 대한 실시간 성능 메트릭 확인

```
sudo apt install stress sysstat
```
<br>

### 🐺 Test

- 3개의 터미널

```
# root 계정으로 전환
sudo su root
cd ~
```

1. 실습 시작전 평균 부하 확인
2. 
![img-2](https://github.com/user-attachments/assets/f998f0f0-e9b2-45c5-abf0-c50231e20c5b)

<br>

### 🐶 시나리오 1 : CPU를 많이 사용하는 프로세스

```
# CPU 사용량이 100% 인 시나리오 시뮬레이션
# terminal 1
stress --cpu 1 --timeout 600

# terminal 2
uptime

# terminal 3
mpstat -P ALL 5
pidstat -u 5 1
```

![img-3](https://github.com/user-attachments/assets/71bc77c3-8060-4150-90c0-2c3816e7d024)
→ 평균 부하가 1.00 으로 천천히 증가

<br><br>
![img-4](https://github.com/user-attachments/assets/6ddeb223-0e21-4d6f-af88-5d8f72d539e8)

![img-5](https://github.com/user-attachments/assets/0888e363-c74d-4126-be46-3defe1bd352e)

→ stress 프로세스의 CPU 사용량이 100%임

<br><br>

### 🐵 시나리오 2 : I/O 집약적 프로세스

```
# terminal 1
stress -i 1 --timeout 600

# terminal 2
watch -d uptime

# terminal 3
mpstat -P ALL 5 1
pidstat -u 5 1
```

![img-6](https://github.com/user-attachments/assets/d767620e-204a-4b7d-bfbd-513a7f6f2393)

![img-7](https://github.com/user-attachments/assets/dcbeb131-362f-42be-9288-bcad915157a4)

![img-8](https://github.com/user-attachments/assets/802bb2bd-699e-45f1-8da9-a8004aeb2f89)

→ 평균 부하가 1.00 을 넘어 증가함

→ 평균 로드의 증가 이유는 iowait의 증가로 인함

→ 여전히 stress 과정에 의해 발생함

<br>

### 🐮 시나리오 3 : 프로세스 수가 많은 시나리오

```
# 시스템에서 실행 중인 프로세스 수가 CPU의 처리 용량을 초과 할 시
CPU를 기다리는 프로세스 발생

# terminal 1
stress -c 8 --timeout 600

# terminal 2
uptime

# terminal 3
pidstat -u 5 1
```
![img-9](https://github.com/user-attachments/assets/5e999826-0fe9-4863-8de0-30f187a648c9)

![img-10](https://github.com/user-attachments/assets/12217ac9-de12-4ac5-8c99-d6706af3ca54)

→ 8개의 프로세스가 2개의 CPU를 나눠 사용하여 최대 75%의 시간동안 CPU 대기(wait) 함

→ CPU 과부하

<br>

## 🐹 결론

- 평균 부하가 높은 것은 CPU를 많이 사용하는 프로세스로 인해 발생
- 평균 부하가 높다고 CPU 사용량 높은 것은 아님
- 또한, I/O 작업이 증가했음을 나타낼 수도 있음
- mpstat, pidstat 와 같은 도구를 사용하여 부하의 원인을 분석 가능

<br>

## 🐯 참고
<https://blog.devgenius.io/deep-understanding-of-average-load-in-linux-74822e1dbcb1>

<br><br>
