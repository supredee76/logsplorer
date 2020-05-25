# Getting Started

### The Program: Logsplorer

This program help explore access logs generated from Loggerator. 
* Once Logsplorer running, it will keep pinging Loggerator running at localhost:8080. 
* After connection estabished, Logsplorer will start to ingest logs into the running in-memory H2 database. The process involve read, parse, save and index the logs.
* The logs are pased according to specification on [The Common Apache Access Log Format](https://www.w3.org/Daemon/User/Config/Logging.html)
* Once the ingestion started, you can start querying the logs, however, the result will be incompleted.
* When ingestion completed, connection to Loggerator will terminate and Logsplorer will never connect to it again. This is by design as per the requirements in [INSTRUCTION.md](https://github.com/supredee76/logsplorer/blob/master/INSTRUCTIONS.md).

#### Prerequisite
You need Java setup on your machine. Read this [guide](https://www.wikihow.com/Run-a-.Jar-Java-File) for more detail.

#### Running the program
1. Fromt your terminal.
2. Navigate to logsplorer-[VERSION].jar file attached in the email and from the project root directory.
3. Run command
```bash
    $ java -jar THE_FILE_NAME.jar
```
4. Now Logsplorer will boot up this should take a few second.

#### Querying the logs
* You can access Logsplorer using any browser or curl from terminal.
* URL `localhost:8443/logs?<QUERY_PARAMS>`
* The query will return a filtered subset of logs received in descending order by date.
* Query params supported are:
    * code, e.g. (code=503)
    * method, e.g. (method=GET)
    * user, e.g. (user=randybishop)

#### The Common Apache Access Log Format
The common logfile format is as follows:

    remote_host rfc931 authuser [timestamp] "request" status bytes
* __remote_host__ - Remote hostname (or IP number if DNS hostname is not available, or if DNSLookup is Off.
* __rfc931__ - The remote logname of the user.
* __authuser__ - The username as which the user has authenticated himself.
* __[timestamp]__ - Date and time of the request.
* __"request"__ - The request line exactly as it came from the client.
    * __method__ - Request method
    * __request resource__ - Client requested rresource
    * __protocol__ - Request protocol
* __status__ - The HTTP status code returned to the client.
* __bytes__ - The content-length of the document transferred.

### Reference Documentation
For further reference, please consider the following sections:

* [Access Log](https://www.w3.org/Daemon/User/Config/Logging.html)
* [H2 Console](http://localhost:8443/h2-console)
* [H2 Features](http://www.h2database.com/html/features.html)

#### To compile and run Logsplorer locally
* Compile: `mvn clean install`
* Run locally (with hot reload): `mvn spring-boot:run` 