# the first stage of our build will extract the layers
FROM openjdk:17-slim-buster as builder

MAINTAINER M.Chikuruwo

EXPOSE 8765

ADD target/zuul-gateway-service-0.0.1-SNAPSHOT.jar /opt/zuul-gateway-service-0.0.1-SNAPSHOT.jar

WORKDIR /opt

ENTRYPOINT ["java", "-jar", "zuul-gateway-service-0.0.1-SNAPSHOT.jar", "&"]