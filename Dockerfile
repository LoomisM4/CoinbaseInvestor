FROM openjdk:11.0-jre-slim
LABEL maintainer="Marcel Wettach <mail@marcel-wettach.com>"
LABEL description="Automatically buys crypto currencies"

RUN mkdir /etc/coinbase
VOLUME /etc/coinbase

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
