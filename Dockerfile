FROM openjdk:17-jdk AS build
WORKDIR /tmp
COPY . /tmp
RUN chmod +x ./gradlew && ./gradlew clean bootJar
FROM openjdk:17-jdk
WORKDIR /tmp
COPY --from=build /tmp/build/libs/sws-0.0.1-SNAPSHOT.jar /tmp/sws.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /tmp/sws.jar"]

