# CISE emu

The CISE Emulator is an application to receive and send tracks messages, conform to the CISE Protocol. 

## Requirements

The application should preferably installed on a linux machine.
Theoretically should work also in any Operative System supporting Java and having a bash shell support but it has only been tested in a GNU/Linux server.

The server should be installed with:

- Java 1.8
- Maven 3.5+
- npm / nodejs tool (see https://webgate.ec.europa.eu/CITnet/confluence/display/OCNET/Ubuntu+Workstations)

## Compilation

Clone the git repository:

```bash
...$ git clone https://webgate.ec.europa.eu/CITnet/stash/scm/marex/cise-emu.git
```

After cloning the git repository is possible to compile the project using maven.

```bash
...$ cd cise-emulator
...$ mvn clean install
```

## Running the emulator

When the compilation will be finished (with a BUILD SUCCESSFUL in the standard output) it will be created a distribution package that can be used to run the software.

```bash
...$ cd target
...$ mv cise-emulator-bin.tar.gz /my/installation/path
...$ cd /my/installation/path
...$ tar xvfz cise-emulator.tar.gz
...$ cd cise-emulator-bin
```

### Command Line Interface (CLI) Emulator
Let's assume the repo base dire is called ``cise-emulator``, in the following chapters 

#### CLI Server Emulator 

To start the CLI server in order to receive messages: 
 
```bash
...$ java -jar ./cise-emulator-app/target/cise-emulator-app-1.1-SNAPSHOT-cli.jar cliserver
```

#### CLI Client Emulator
To launch a CLI client in order to send messages:

```bash
...$ java -jar ./cise-emulator-app/target/cise-emulator-app-1.1-SNAPSHOT-cli.jar  sender -c ./cise-emulator-assembly/src/main/conf/cliconfig.yml -s ./cise-emulator-assembly/src/main/conf/xmlmessages/PushTemplate.xml
```

### Web Application (WEB) Emulator

To work properly the emulator needs to refer to an absolute path where to find the Java key store.
Therefore you need to copy the jks file to local directory:

```bash
...$ mkdir  -p /opt/jboss/EuciseData/sim-egn/conf
...$ cp ./cise-emulator-assembly/src/main/conf/keyStore.jks /opt/jboss/EuciseData/sim-egn/conf/apache-nodecx.jks
```
The web application of the emulator is composed by two different parts: the API server and the react front end interface. 

#### WEB API server
To start the web api server from the terminal:

```bash
...$ java -jar ./cise-emulator-app/target/cise-emulator-app-1.1-SNAPSHOT-web.jar  server ./cise-emulator-assembly/src/main/conf/config.yml &
```

#### WEB: initialization of the nodejs server
The nodejs server will server the HTML and javascript file during the development. 
To initialize it: 

```bash
...$ cd cise-emulator-react/
...$ npm install
...$ npm run build --scripts-prepend-node-path=auto  
```

#### WEB start the development server  
Launching this command will open the browser, fire up a nodejs and serve the HTML+JS files:
 
```bash
...$ npm run start --scripts-prepend-node-path=auto  
```