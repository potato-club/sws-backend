FROM openjdk:17-jdk AS build
WORKDIR /app
COPY . /app
RUN chmod +x ./gradlew
RUN microdnf install -y findutils
RUN ./gradlew bootJar
#ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app/build/libs/sws-0.0.1-SNAPSHOT.jar"]
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -Djasypt.encryptor.password=${JASYPT_ENCRYPTOR_PASSWORD} -jar /app/build/libs/sws-0.0.1-SNAPSHOT.jar"]
