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