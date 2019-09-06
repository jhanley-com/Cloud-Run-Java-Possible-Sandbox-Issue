FROM anapsix/alpine-java
MAINTAINER www.jhanley.com 
COPY main.jar /main.jar
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/main.jar"]
