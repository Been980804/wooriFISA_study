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