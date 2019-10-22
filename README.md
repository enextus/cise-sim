# CISE Sim

The "CISE Sim" is an application to receive and send messages conform to the CISE Protocol.
It simulate the node and adapter :
-  common behaviour in message sending (messageId-CorrelationId / creationdate / signature)
-  reception handling with synchronous acknowledgement including basic returned error  (excluded registration, security matrix related error)  

## Requirements

The application must  be installed on a linux machine.
Theoretically should work also in any Operative System supporting Java but it have only been tested in a GNU/Linux server.

The server should be installed with:

- Java 1.8

## Running the simulator server

When the compilation will be finished (with a BUILD SUCCESSFUL in the standard output) it will be created a distribution package that can be used to run the software.

```bash
mkdir /my/installation/path -p
tar -xvzf cise-sim-*.tar.gz -C /my/installation/path --strip-components=1
cd cise-sim-bin
./sim run
```

## Configuration of the simulator server

before running ( and after compilation ) 
distribution package can be tailored to run the software accordingly to specific needs.

- by modifying the sim.properties file (common application properties)
```bash
...$ cd /my/installation/path
...$ nano conf/sim.properties
```

| Property key  | Description  | Example value |
| :------------ |:---------------:| -----:|
| aproperty      | some wordy text | anyValue |
| bproperty      | centered        |   anyValue |
| cproperty | are neat        |    anyValue |

- by modifying the config.yml file (internal application yml file)
```bash
...$ cd /my/installation/path
...$ nano conf/sim.properties
```
| Property key  | Description  | Example value |
| :------------ |:---------------:| -----:|
| aproperty      | the server port to make accessible the web interface and cise API.  | anyValue |
| bproperty      | centered        |   anyValue |
| cproperty | are neat        |    anyValue |

## Compilation (*)
To compil from source source code will be also required :

- Maven 3.5+
- npm 6.4.1 / nodejs 10.11.0 tool  
If any proxy is required by your network maven script require proxy configuration
to compile download node.js & npm automatically (https://maven.apache.org/guides/mini/guide-proxies.html)

After cloning the git repository it is possible to compile the project using maven.

```bash
...$ cd cise-emulator
...$ mvn clean install
```
If npm compilation is not wanted just add the following profile directive:

```bash
...$ cd cise-emulator
...$ mvn clean install -P cibuild
```
