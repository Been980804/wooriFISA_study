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
