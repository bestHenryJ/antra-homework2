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
- Endpoint : http://localhost:8200/weather/search/info
- Description: A searchInfo restful api to implement fetch student info from student service and fetch 3rd part api from university service.
- Method : GET
- Implementation:
```
   public Map<String, Object> searchInfoOfStudentAndUniversity(long id, String name) {
        Map<String, Object> map = new HashMap<>();
        CompletableFuture<Void> searchStudent = CompletableFuture.supplyAsync(()->{
            String url = "http://localhost:8200/students/" + id;
            Student student = restTemplate.getForObject(url, Student.class);
            System.out.println(student);
            if (student == null) throw new ResourceNotFoundException("student", "id", String.valueOf(id));
            return student;
        }).thenAccept((result) -> {
            map.put("Student " + id, result);
        }).exceptionally((e) -> {
            e.printStackTrace();
            return null;
        });

        CompletableFuture<Void> searchUniversity = CompletableFuture.supplyAsync(()-> {
            String url = "http://localhost:8200/university?name=" + name;
            University university = restTemplate.getForObject(url, University.class);
            if (university == null) throw new ResourceNotFoundException("university", "name", name);
            return university;
        }).thenAccept((result)->{
            map.put("university " + name, result);
        }).exceptionally((e) -> {
            e.printStackTrace();
            return null;
        });

        CompletableFuture<Void> allQuery = CompletableFuture.allOf(searchStudent,searchUniversity);
        CompletableFuture<Map<String, Object>> future = allQuery.thenApply((result) -> {
            return map;
        }).exceptionally((e) -> {
            e.printStackTrace();
            return null;
        });

        future.join();

        return map;
```
## 4. Configure Hystrix (circuit) in one service
- Configure Hystrix in university service
```
    @Override
    @HystrixCommand(fallbackMethod = "nameFallBack",
            commandProperties = {
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "3000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "30"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            }
    )
    public String getByName(String name) {
        String url = UNIVERSITY_API_URL + "/search?name=" + name;
        List<University> universityList = restTemplate.getForObject(url, List.class);
        if (universityList.isEmpty()) {
            log.error("no resource found");
            throw new RecourseNotFoundException("request info", "name", name);
        }
        return universityList.toString();
    }
    public UniversityDTO mapToDTO(University university) {
        UniversityDTO universityDTO = new UniversityDTO();
        universityDTO.setName(university.getName());
        universityDTO.setCode(university.getCode());
        universityDTO.setCountry(university.getCountry());
        universityDTO.setWebpages(university.getWebPages());
        return universityDTO;
    }
    public String nameFallBack(String name){
        log.info("fallback function is excuted");
        return "The System is busy ! Please try again later";
    }
```
## 5. Configure swagger documentation in search service
```
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.search.controller"))
                .build();
    }
    private ApiInfo apiInfo(){
        Contact contact = new Contact("HJ", "https://github.com/bestHenryJ", "xxxxx@gmail.com");
        return new ApiInfo(
                "Homework SwaggerAPI document",
                "antra project weather",
                "v1.0",
                "https://github.com/bestHenryJ",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
```
## 6. Configure student & Third party service in gateway
```
server.port=8200

spring.cloud.gateway.routes[0].id=weatherModule
spring.cloud.gateway.routes[0].uri=lb://search
spring.cloud.gateway.routes[0].predicates[0]=Path=/weather/**

spring.cloud.gateway.routes[1].id=detailsModule
spring.cloud.gateway.routes[1].uri=lb://details
spring.cloud.gateway.routes[1].predicates[0]=Path=/details/**
#spring.cloud.gateway.routes[1].filters[0]=StripPrefix=1

spring.cloud.gateway.routes[2].id=studentModule
spring.cloud.gateway.routes[2].uri=lb://student
spring.cloud.gateway.routes[2].predicates[0]=Path=/students/**

spring.cloud.gateway.routes[3].id=universityModule
spring.cloud.gateway.routes[3].uri=lb://university
spring.cloud.gateway.routes[3].predicates[0]=Path=/university/**

spring.cloud.gateway.discovery.locator.enabled=true

myservice.ribbon.NFLoadBalancerRuleClassName=com.netflix.loadbalancer.RandomRule

eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
#eureka.client.registryFetchIntervalSeconds=xx
#ribbon.ServerListRefreshInterval

#actuator endpoint
management.endpoints.web.exposure.include=*

#indicates the frequency the client sends heartbeat to server to indicate that it is alive.
eureka.instance.lease-renewal-interval-in-seconds=10
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
```
