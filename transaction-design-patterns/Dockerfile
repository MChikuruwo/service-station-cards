# the first stage of our build will extract the layers
FROM openjdk:17-slim-buster as builder

EXPOSE 9301

ADD target/transaction-design-patterns-0.0.1-SNAPSHOT.jar /opt/transaction-design-patterns-0.0.1-SNAPSHOT.jar

WORKDIR /opt

COPY . /opt


ENTRYPOINT ["java", "-jar" , "transaction-design-patterns-0.0.1-SNAPSHOT.jar", "&"]
