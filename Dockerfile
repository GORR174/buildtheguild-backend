FROM maven:3.8.7-openjdk-18
COPY target/build-the-guild-*.jar build-the-guild.jar
ENTRYPOINT ["java", "-jar", "/build-the-guild.jar"]