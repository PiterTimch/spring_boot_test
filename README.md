# web_site
```
mvn spring-boot:run

mvn clean package

java -jar target/java-web-mvc.jar
```

## Run docker
```
docker build -t mvc-java .

docker run -it --rm -p 6892:8081 --name mvc-java_container mvc-java

docker run -d --restart=always -p 6892:8081 --name mvc-java_container mvc-java
 
docker run -d --restart=always -p 6892:8081 --name mvc-java_container mvc-java

docker run -d --restart=always -v d:/volumes/mvc-java/uploads:/app/uploads -p 6892:8081 --name mvc-java_container mvc-java

docker run -d --restart=always -v /volumes/mvc-java/uploads:/app/uploads -p 6892:8081 --name mvc-java_container mvc-java

docker login
docker tag mvc-java:latest pedro007salo/mvc-java:latest
docker push pedro007salo/mvc-java:latest

ls -l mvc-java.sh
chmod +x mvc-java.sh
sh mvc-java.sh
```