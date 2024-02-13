#!/usr/bin/env bash

REPOSITORY=/home/ubuntu/app
PORT=8080

echo "> 현재 구동 중인 애플리케이션 확인"

if lsof -ti:$PORT &> /dev/null; then
  echo "> 현재 $PORT 포트에서 실행 중인 애플리케이션 종료"
  kill $(lsof -ti:$PORT)
  sleep 5
else
  echo "현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
fi

echo "> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/*SNAPSHOT.jar | tail -n 1)

echo "> JAR NAME: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

export JASYPT_ENCRYPTOR_PASSWORD=$(aws ssm get-parameter --name /profileem/JASYPT_ENCRYPTOR_PASSWORD --query Parameter.Value --output text)

nohup java -jar -Duser.timezone=Asia/Seoul -Djasypt.encryptor.password=$JASYPT_ENCRYPTOR_PASSWORD $JAR_NAME >> $REPOSITORY/nohup.out 2>&1 &
