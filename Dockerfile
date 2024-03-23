FROM openjdk:17-jdk AS build
WORKDIR /tmp
COPY . /tmp
RUN yum update -y && yum install -y findutils && chmod +x ./gradlew && ./gradlew clean bootJar

# 생성한 jar 파일을 실행함.
FROM openjdk:17-jdk
WORKDIR /tmp
COPY --from=build /tmp/build/libs/sws.jar /tmp/sws.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /tmp/sws.jar"]