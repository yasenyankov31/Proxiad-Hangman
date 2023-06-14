FROM openjdk:19
EXPOSE 8080
COPY target/spring-boot-docker.war spring-boot-docker.war 
COPY wordlist.txt wordlist.txt 
ENTRYPOINT ["java","-jar","/spring-boot-docker.war"]