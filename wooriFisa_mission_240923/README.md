# 동적 디렉토리 생성 & cron 활용 자동 백업

<br>

## 미션 내용

- step02shell 하위에 fisa1 ~ fisa5까지의 dir
    
    shell script개발
    
    반복
    
    mkdir
    
- step02shell dir을 매일 오전 9시에 백업 자동화


<br>

## 동적 디렉토리 생성

```
#!/bin/bash

for i in `seq 1 5`
do
    filename=fisa$i
    if [ ! -d "fisa$i" ]
    then
        echo "생성"
        `mkdir $filename`
    else
        `rmdir $filename`
        echo "삭제 후 재생성"
        `mkdir $filename`
    fi
done
exit 0
```

### 실행 전

![img-1](https://github.com/user-attachments/assets/5419143a-01c8-4518-96c1-3294183c7dde)

### 실행 후

![img-2](https://github.com/user-attachments/assets/78da5379-6940-492a-ab31-ecf29ac653fd)

<br>

## step02shell dir 백업

```
#!/bin/bash

if [ ! -d "backup" ]
then
    `mkdir backup`
fi

for i in `seq 1 5`
do 
    SOURCE_DIR="/home/username/step02shell/fisa"$i
    BACKUP_DIR="/home/username/step02shell/backup"

    DATE=$(date +"%Y-%m-%d_%H-%M-%S")
    BACKUP_FILE="${BACKUP_DIR}/backup_${DATE}_fisa$i.tar.gz"

    if  [ -d "$SOURCE_DIR" ]
        then
            tar -czf "$BACKUP_FILE" -C "$SOURCE_DIR" .
            echo "백업되었습니다 : $BACKUP_FILE"
        else
            echo "디렉토리가 존재하지 않습니다 ㅣ $SOURCE_DIR"
        fi
done
```

### 실행 전

![img-3](https://github.com/user-attachments/assets/78da5379-6940-492a-ab31-ecf29ac653fd)

### 실행 후

![img-4](https://github.com/user-attachments/assets/84e1e79f-c7df-4772-a90b-7e620add2b10)

