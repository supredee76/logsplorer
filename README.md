# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/maven-plugin/reference/html/#build-image)
* [Simple REST APPI Resource](https://spring.io/guides/gs/rest-service/)
* [Deploying Spring boot application](https://spring.io/blog/2014/03/07/deploying-spring-boot-applications)
* [Access Log](https://www.w3.org/Daemon/User/Config/Logging.html)
* [H2 Console](http://localhost:8443/h2-console)
* [H2 Features](http://www.h2database.com/html/features.html)
#### Run Application locally
./mvnw spring-boot:run __or__ mvn spring-boot:run (with hot reload)

#### The Common Logfile Format
The common logfile format is as follows:

    remote_host rfc931 authuser [date] "request" status bytes
* __remote_host__ - Remote hostname (or IP number if DNS hostname is not available, or if DNSLookup is Off.
* __rfc931__ - The remote logname of the user.
* __authuser__ - The username as which the user has authenticated himself.
* __[date]__ - Date and time of the request.
* __"request"__ - The request line exactly as it came from the client.
    * __method__ - Request method
    * __request resource__ - Client requested rresource
    * __protocol__ - Request protocol
* __status__ - The HTTP status code returned to the client.
* __bytes__ - The content-length of the document transferred.

#### Search and kill running process using port
netstat -ano | findstr :PORT_NUMBER
taskkill /PID PPROCESS_NUMBER /F