# 1. 자바 17 버전 사용
FROM openjdk:21

# 2. 환경변수 '자리'만 만들어둡니다.
# (진짜 비밀번호와 IP는 나중에 구글 클라우드에서 안전하게 주입해 줄 겁니다!)
ENV DB_PASSWORD=""
ENV DB_URL="jdbc:mysql://DB_IP:3306/resqpot"
ENV DB_USERNAME="root"

# 3. 빌드된 jar 파일을 복사합니다.
COPY build/libs/*-SNAPSHOT.jar /app.jar

# 4. 스프링 부트 실행
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/app.jar"]