# 자주 사용하는 값 변수에 저장
REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=only-camper-gift
OLD_PROJECT_NAME=springboot-webservice

# git clone 받은 위치로 이동
cd $REPOSITORY/$PROJECT_NAME/

# master 브랜치의 최신 내용 받기
echo "> Git Pull"
git pull

# build 수행
echo "> project build start"
./gradlew clean
./gradlew build

echo "> directory로 이동"
cd $REPOSITORY

# build의 결과물 (jar 파일) 특정 위치로 복사
echo "> build 파일 복사"
cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)

echo "> 현재 구동중인 애플리케이션 pid: $CURRENT_PID"
if [ -z "$CURRENT_PID" ]; then
	echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
	echo "> kill -15 $CURRENT_PID"
	kill -15 $CURRENT_PID
	sleep 5
fi

echo "> 새 애플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)

echo "> Jar Name: $JAR_NAME"
nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &
