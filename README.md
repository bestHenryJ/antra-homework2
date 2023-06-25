# The Objective Of Homework2
## 1. Add 2 rest applications into project
### Student Service 
Implement CRUD funciton for student service and mutiple table query function(student to teacher).
### University Service(University)
Implement two restful api for searchByName and searchByCountry for university serivce through using third party api.
## 2. Use config service to load configuration
Create two properties for student and university for config service to load configuration.
- Student Service (include server port, database config, eureka config and log config)
```
server.port=8362

spring.datasource.url=jdbc:mysql://localhost:3306/dbone?serverTimezone=UTC&characterEncoding=utf8
spring.datasource.username=root
spring.datasource.password=jh2120465

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true

#actuator endpoint
management.endpoints.web.exposure.include=*

#indicates the frequency the client sends heartbeat to server to indicate that it is alive.
eureka.instance.lease-renewal-interval-in-seconds=10

eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/

logging.level.root=INFO
logging.level.org.springframework.web=ERROR
logging.level.org.hibernate=DEBUG
logging.file.path=student-log
logging.file.name=student-log/studentlog.log
```
- University Service (include server port, eureka config and log config)
```
server.port=8886

eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true

#actuator endpoint
management.endpoints.web.exposure.include=*

#indicates the frequency the client sends heartbeat to server to indicate that it is alive.
eureka.instance.lease-renewal-interval-in-seconds=10

eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/

logging.level.root=INFO
logging.level.org.springframework.web=DEBUG
logging.file.path=university-log
logging.file.name=university-log/universitylog.log
```
## 3. Use CompletableFuture + restTemplate in search service
## 4. Configure Hystrix (circuit) in one service
## 5. Configure swagger documentation in search service
## 6. Configure student & Third party service in gateway
