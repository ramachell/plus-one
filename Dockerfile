# amazon 머신에 배포할 예정이기 때문에 아마존 머신 사용
# 아마존에서 만든 java sdk사용
# sdk install java 17.0.9-amzn
FROM amazoncorretto:17 AS builder
COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew bootJar
COPY ./build/libs/plusone-0.0.1-SNAPSHOT-plain.jar /app.jar

ENV TZ="Asia/Seoul"

ENTRYPOINT ["echo", "you must specify entrypoint"]
