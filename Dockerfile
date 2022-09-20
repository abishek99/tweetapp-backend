FROM openjdk:11
EXPOSE 8090
ADD target/tweetapp.jar tweetapp.jar
ENTRYPOINT ["java","-jar","/tweetapp.jar"]