# WooriFisa-crontab

---

## 주제 탐색

- 도서관 연체 안내(웹 서비스)
- 중요 데이터 백업
- 오래된 로그 파일 정리
- 회의 알람 서비스 ✅


## 내용

회의날짜와 시간을 입력하면 10분전에 알람 메일을 보내는 서비스

### 1. set_meeting_alarm.sh 설정

```
#!/bin/bash

# 회의 시간과 날짜 입력 받기
echo "회의 날짜와 시간을 입력하십시오. (형식: YYYY-MM-DD HH:MM) : "
read meeting_time

# 사용자 이메일 입력 받기
echo "알람 받을 이메일 주소를 입력하세요 : "
read user_email

# 입력받은 시간을 Unix 타임스탬프로 변환 (Unix 타임스탬프: 초 단위로 계산)
meeting_timestamp=$(date -d "$meeting_time" +%s)

# 타임스탬프 변환 오류 처리
if [ $? -ne 0 ]; then
    echo "잘못된 시간 형식입니다. 다시 입력해 주세요. (형식: YYYY-MM-DD HH:MM)"
    exit 1
fi

# 회의 10분 전 시간 계산
alarm_timestamp=$(($meeting_timestamp - 600))

# 알람 시간을 사람이 읽을 수 있는 crontab 형식으로 변환
minute=$(date -d @$alarm_timestamp +'%M')
hour=$(date -d @$alarm_timestamp +'%H')
day=$(date -d @$alarm_timestamp +'%d')
month=$(date -d @$alarm_timestamp +'%m')

# crontab에 작업 추가 (기존 작업 유지)
(crontab -l 2>/dev/null; echo "$minute $hour $day $month * echo '회의 시작 10분 전 입니다.' | mail -s '회의 알림' $user_email") | crontab -

# 성공 메시지
echo "회의 알람이 설정되었습니다."
```
![1](https://github.com/user-attachments/assets/8bb97590-47f0-4a63-a2b1-63bf3786cf29)


### 2. 스크립트 실행 권한 부여

```
chmod +x set_meeting_alarm.sh
```

### 3. 스크립트 실행

```
./set_meeting_alarm.sh
```

### 4. 회의 시간 및 날자 입력
![2](https://github.com/user-attachments/assets/867c49ef-7906-4fc0-a285-2e7d48d17740)




### 5. 메일 보내기위한 SMTP 설정

```
sudo vi /etc/postfix/main.cf

relayhost = [smtp.gmail.com]:587  

# 하단에 추가
smtp_sasl_auth_enable = yes
smtp_sasl_security_options = noanonymous
smtp_tls_security_level = may
smtp_tls_CAfile = /etc/ssl/certs/ca-certificates.crt
smtp_sasl_password_maps = hash:/etc/postfix/sasl_passwd

```
![3](https://github.com/user-attachments/assets/a26cecbd-0ccf-450b-9d6c-49cf33484d55)


```
sudo nano /etc/postfix/sasl_passwd

[smtp.gmail.com]:587 your-email@gmail.com:your-password
```




### 6. Postfix가 읽을 수 있게 Hash 테이블로 변경 및 파일 권한 설정

```
sudo postmap /etc/postfix/sasl_passwd

sudo chmod 600 /etc/postfix/sasl_passwd /etc/postfix/sasl_passwd.db
```

### 7. Postfix 재시작

```
sudo systemctl restart postfix
```

### 8. 이메일 전송 테스트

```
echo "테스트 메시지입니다." | mail -s "테스트 메일" your-email@example.com
```
![4](https://github.com/user-attachments/assets/03877622-2ac7-4432-bd45-0046a94b00bd)


---

## 지나간 Crontab 삭제 (끝난 회의)

### 1. delete_meeting_alarm.sh 설정

```
#!/bin/bash

# 현재 날짜와 시간을 Unix 타임스탬프로 저장
current_time=$(date +%s)

# 새로운 crontab을 임시 파일에 저장
temp_crontab=$(mktemp)

# crontab에서 작업 목록 가져오기
crontab -l | while read -r line; do
    # 시간이 지정된 줄인지 확인 (주석이나 빈 줄 등 제외)
    if echo "$line" | grep -P '^\s*\d+\s+\d+\s+\d+\s+\d+\s+\*' > /dev/null; then
        # 분, 시간, 일, 월 추출
        minute=$(echo "$line" | awk '{print $1}')
        hour=$(echo "$line" | awk '{print $2}')
        day=$(echo "$line" | awk '{print $3}')
        month=$(echo "$line" | awk '{print $4}')

        # 해당 시간을 Unix 타임스탬프로 변환
        cron_time=$(date -d "$month/$day $hour:$minute" +%s)

        # 현재 시간과 비교하여 과거에 있는 작업이면 삭제
        if [ "$cron_time" -lt "$current_time" ]; then
            echo "지나간 작업 삭제: $line"
        else
            # 지나가지 않은 작업은 임시 crontab 파일에 저장
            echo "$line" >> "$temp_crontab"
        fi
    else
        # 주석이나 기타 명령어는 그대로 임시 crontab 파일에 저장
        echo "$line" >> "$temp_crontab"
    fi
done

# 새 crontab 파일 적용
crontab "$temp_crontab"

# 임시 파일 삭제
rm "$temp_crontab"

```
![5](https://github.com/user-attachments/assets/91130230-f247-4805-8357-96fa48c24da6)


### 2. crontab에 추가

```
crontab -e

# 매일 아침 9시에 실행
0 9 * * * ./crontab/delete_meeting_alarm.sh
```

### 3. 실행 권한 설정

```
chmod +x ./crontab/delete_meeting_alarm.sh
```

### 4. Test

```
./crontab/delete_meeting_alarm.sh
```
![6](https://github.com/user-attachments/assets/7da75e5d-df74-4b23-b27c-37f1ab648b6e)
