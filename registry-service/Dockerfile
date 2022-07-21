# the first stage of our build will extract the layers
FROM openjdk:17-slim-buster as builder

MAINTAINER M.Chikuruwo

EXPOSE 8761

ADD target/eureka-service-0.0.1-SNAPSHOT.jar /opt/eureka-service-0.0.1-SNAPSHOT.jar

WORKDIR /opt

ENTRYPOINT ["java", "-jar", "eureka-service-0.0.1-SNAPSHOT.jar", "&"]