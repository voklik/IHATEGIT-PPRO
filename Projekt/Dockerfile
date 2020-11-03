FROM openjdk:8
ADD target/Projekt.jar Projekt.jar
EXPOSE 9000
ENTRYPOINT ["java","-jar","Projekt.jar"]


ADD script.sh /tmp/script.sql
RUN /tmp/script.sql

