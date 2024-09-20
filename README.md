# wooriFisa_PAM

# PAM 모듈 활용하여 비밀번호를 8자리 이상으로 규제하기

## 1. pam_pwquality 모듈 설치
```
sudo apt-get install libpam-pwquality
```

## 2. pam 설정 파일 수정
```
sudo vi /etc/pam.d/common-password

# 추가
password requisite pam_pwquality.so retry=3 minlen=8
# 8자리 이상, 3번 반복
```
![image](https://github.com/user-attachments/assets/1c5bee27-7f4e-4c72-9d17-a224465d840a)

## 3. 테스트
```
passwd

Current password : [현재 비밀번호]
New password : [새 비밀번호]
Retype new password : [새 비밀번호 확인]
```
![image](https://github.com/user-attachments/assets/da7daa7a-f64d-41ca-9b11-4d4950a49608)


## 4. 비밀번호 입력 실패
![image](https://github.com/user-attachments/assets/06c5be36-9127-47bc-b989-830d9a1f5ee1)

- 8자리 미만인 경우
- 같은 단어가 반복될 경우

## 5. 트러블슈팅
```
password requisite pam_pwquality.so retry=3 minlen=8
를 추가 할 시 pam.unix.so 아래에 추가 시 error 발생
```
