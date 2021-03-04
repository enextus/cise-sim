# CISE Sim

**CISE Sim** is an application capable of sending and receiving CISE messages to/from CISE Nodes, adaptors or other CISE Sims. The CISE Sim is conformant to the CISE Service model.

## Functionalities
- send CISE messages using a template 
- receive CISE messages
- validate the CISE messages according to the CISE Data and Service models.
- store sent/received messages
- display the message history and the message threads (messages chains) 
- discover CISE services from a CISE Node 


## Endpoints
The CISE Sim exposes the following endpoints:

| Endpoint|Description 
|---|---
|``http://HOST_ADDRESS:8200/``| Web interface (for web browsers) 
|``http://HOST_ADDRESS:8200/api/messages`` | REST interface (to **receive** CISE messages from other adaptors/nodes/CISE Sim)
|``http://HOST_ADDRESS:8200/api/soap/messages``| SOAP interface (to **receive** CISE messages from other adaptors/nodes/CISE Sim)

>
> The CISE Sim can receive CISE messages from the REST and the SOAP endpoints at the same time.
>
> To change the default port (```8200```), please check the `config.yml` file.
>

# Installation

## Requirements
- GNU/Linux operative system
- Java 8 or Java 11
- Docker version 19 at least (optional)
- docker-compose version 1.25 at least (optional)


## Software Packages

The CISE Sim is packaged and disbributed as:
- a tar.gz archive: cise-sim-``VERSION``.tar.gz
- a Docker container

## TAR.gz Archive 

Untar the cise-sim-``VERSION``.tar.gz in a folder:

```bash
$ mkdir -p /my/installation/path 
$ tar -xvzf cise-sim-VERSION.tar.gz -C /my/installation/path --strip-components=1
```

Set up `$PATH` and `$JAVA_HOME` variables
```bash
$ export PATH=/path/to/java/bin:$PATH
$ export JAVA_HOME=/path/to/java
```

## Docker container
To use docker image, the following version of docker and docker-compose are needed:  

**docker version 19 at least**  
**docker-compose version 1.25 at least**  

The name of the docker image is 

**ec-jrc/cise-sim:latest**

### Install the docker image 
To install the docker image in the local docker images repository, use the following command:
```
docker load < docker_cisesim_1.3.0.tar.gz
```

### Set up Docker volumes

Create in the host the following folders in any location
(e.g., under /my/installation/path):

| Folder | Folder in Docker | Description
| --- | --- | ---
| /my/installation/path/``conf`` | /srv/cise-simulator/``conf`` | Configuration files
| /my/installation/path/``logs`` | /srv/cise-simulator/``logs`` | Logs
| /my/installation/path/``msghistory`` | /srv/cise-simulator/``msghistory`` | Sent/received CISE messages

```bash
$ mkdir -p /my/installation/path/conf
$ mkdir -p /my/installation/path/logs
$ mkdir -p /my/installation/path/msghistory
```

 


# Set up the CISE Sim

## Folder structure

```bash
$ ls -l /my/installation/path/
total 128
-rw-r--r-- 1 cise cise 11452 Jul  8 17:24 README.md
drwxr-xr-x 2 cise cise  4096 Jul  6 11:15 conf     # Config files and JKS
drwxr-xr-x 2 cise cise  4096 Jul  6 11:15 docs     # Documentation
drwxr-xr-x 2 cise cise  4096 Jul  6 11:15 lib      # Java libraries
drwxr-xr-x 2 cise cise  4096 Jul  6 11:15 logs  
drwxr-xr-x 2 cise cise  4096 Jul  6 11:15 msghistory # Messages sent/received
-rwxr-xr-x 1 cise cise  4236 Jul  6 11:15 sim        # Bash script to launch the CISE Sim
drwxr-xr-x 4 cise cise  4096 Jul  6 11:15 templates  # Folder with the message templates
```

## Configuration files

The ``conf/`` folder contains three configuration files:

- ``sim.properties``
- ``config.yml``
- Java Key Store (JKS) for message signature.

### sim.properties

This file contains the parameters to set up the protocol, the endpoint and the message signature.

| Parameter|Description|Example|
|----------|-----------|-------|
|simulator.name|Simulator name displayed on the CISE Sim web interface. <br> The property is used only to display the system name on the CISE Sim.<br> The property does not affect the functioning.|sim1-nodeAX
|destination.protocol|Protocol used to send messages to the "destination.url". Allowed values: `SOAP`, `REST`|SOAP
|destination.url| URL of the service endpoint where the CISE Sim will send the XML messages.|http://10.10.10.34:8300/api/soap/messages
|templates.messages.directory|Relative path to the folder with the message templates (from the installation directory).| `templates/messages` (Default value)
|signature.keystore.filename|Filename of the Java Key Store (contained in the ``conf/`` directory).|`cisesim-nodeex.jks`
|signature.keystore.password|Password if the JKS file `signature.keystore.filename`.|12345
|signature.privatekey.alias|Alias for _key pair_ used to  sign the XML messages. The key pair is stored in the in `signature.keystore.filename` |``cisesim-nodeex.nodeex.eucise.ex``
|signature.privatekey.password|Password of the key pair `signature.privatekey.alias`.|12345
|history.repository.directory|Relative path to the folder with the messages sent/received|`msghistory` (Default value)
|history.gui.maxnummsgs|Maximum number of threads displayed in the user interface|10
|proxy.host|IP address of the HTTP Proxy (Optional)|10.10.10.10
|proxy.port|Port number of the HTTP Proxy (Optional)|1234
|discovery.sender.serviceid|Discovery service, Sender ServiceId (Optional)
|discovery.sender.servicetype|Discovery service, Sender ServiceType (Optional)
|discovery.sender.serviceoperation|Discovery service, Sender ServiceOperation (Optional)

Note: Discovery sevice button will be present in the UI, only if all the three parameters discovery.* are presents
#### Example: sim.properties
```properties
#
# CISE Sim (1.3.0-ALPHA)
#

# Simulator name displayed on the CISE Sim web interface.
# The property is used only to display the system name on the CISE Sim.
# The property does not affect the functioning.
simulator.name=eu.eucise.ex.cisesim-nodeex

# Protocol used to send messages to the "destination.url".
# Allowed values: SOAP, REST
destination.protocol=SOAP

# URL of the service endpoint where the CISE Sim will send the messages
destination.url=http://localhost:8300/api/soap/messages

# Relative path to the folder with the message templates
templates.messages.directory=templates/messages

# JKS configuration for message signature
signature.keystore.filename=cisesim-nodeex.jks
signature.keystore.password=cisesim
signature.privatekey.alias=cisesim-nodeex.nodeex.eucise.ex
signature.privatekey.password=cisesim

# Relative path to the folder with the messages sent/received
history.repository.directory=msghistory
# Maximum number of messages displayed in the web interface
history.gui.numthreads=100

# Proxy configuration
# proxy.host=10.40.X.5
# proxy.port=8888

# Discovery service Sender parameters
# ServiceId of the Sender
discovery.sender.serviceid=

# Service Type of discovery.sender
discovery.sender.servicetype=

# Service Type of discovery.sender
discovery.sender.serviceoperation=

```

---

### config.yml

The CISE Sim uses Dropwizard as application server. The file ``config.yml`` defines the parameters for the application server. For additional information on this file, please check the [Dropwizard manual](https://www.dropwizard.io/en/latest/manual/configuration.html).

```yaml
server:
  # Protocol and port of simulator web interface
  applicationConnectors:
    - type: http
      port: 8200
``` 

While the logging information can be found mostly under the ``logging.loggers`` configuration:
```yaml
logging:
  level: INFO
  loggers:
    "io.dropwizard.bundles.assets": INFO
    "eu.cise.sim.api": INFO
    "org.eclipse.jetty.server.handler": WARN
    "org.eclipse.jetty.setuid": WARN
    "io.dropwizard.server.DefaultServerFactory": WARN
    "io.dropwizard.bundles.assets.ConfiguredAssetsBundle": WARN
```
#### Example: config.yml 
```yaml
#
# CISE Sim - server configuration (1.3.0-ALPHA)
#
# The CISE Sim uses Dropwizard as application server. 
# For more information on this configuration file, please check: 
# https://www.dropwizard.io/en/latest/manual/configuration.html
#

server:
# Port used to receive CISE messages and for the Web interface
  applicationConnectors:
    - type: http
      port: 8200
# Administration port
  adminConnectors:
    - type: http
      port: 8201

# Log setup in Dropwizard
logging:
  level: INFO
  loggers:
     "io.dropwizard.bundles.assets": INFO
     "eu.cise.dispatcher": INFO
     "org.apache.cxf": WARN
     "eu.cise.emulator.api": INFO
     "org.eclipse.jetty.server.handler": WARN
     "org.eclipse.jetty.setuid": WARN
     "io.dropwizard.server.DefaultServerFactory": WARN
     "io.dropwizard.bundles.assets.ConfiguredAssetsBundle": WARN
     "org.wiremock": INFO
  appenders:
    - type: console
      threshold: ALL
      queueSize: 512
      discardingThreshold: 0
      timeZone: UTC
      target: stdout
      logFormat: "%highlight(%.-1level)|%message%n"
    - type: file
      threshold: ALL
      logFormat: "%d{HH:mm:ss}[%5.-5thread]%-5.5level|%-25.25logger{1}|%msg%n"
      currentLogFilename: ./logs/sim.log
      archivedLogFilenamePattern: ./logs/sim-%d{yyyy-MM-dd}.log.gz
      archivedFileCount: 5
      timeZone: UTC
```

--- 

### Java Key Store

The CISE Sim is distributed with a sample Java Key Store file. 

To connect the CISE Sim to a CISE Node, the node administrator should create a specific participant for the CISE Sim. The node administrator must provide the JKS file for the participant with the parameters to access the certificates.

> The new JKS file **must** be stored under the ``conf`` folder.


# Launch the CISE Sim

## As a process 

### Start in foreground
To run the application in a foreground process:

```bash
$ cd /my/installation/path
$ ./sim run
```

The application log will be displayed in the standard output.
The CISE Sim will ``stop`` if the terminal session is closed.

### Start in background
To run the application in a background process:

```bash
$ cd /my/installation/path
$ ./sim start
```

Output:
```bash
== CISE sim =====================================
Java path:    /usr/bin/java
Java version: "11.0.7"
=================================================
[ok] sim started at 2020-07-07T17:09:53+02:00
```

With the start command, the CISE Sim will run in background (using nohup) even if the terminal session is closed.   

The application log will be stored in the file ``logs/sim.log``:

```bash
$ cd /my/installation/path
$ tail -f logs/sim.log
```

### Stop a CISE Sim in background
To stop a CISE Sim launched in background:

```bash
$ cd /my/installation/path
$ ./sim stop
```

Output:

```bash
[ok] sim has been stopped
```

### Other commands 
 
```bash
$ cd /my/installation/path
$ ./sim
Usage: sim COMMAND
sim server lifecycle manager (starting, stopping, debugging).
COMMAND
    start       starts the simulator in a detached shell using nohup command.
    run         starts the simulator in foreground.
    stop        stops the simulator running in background.
    restart     restart the simulator running in background.
    debug-start starts the simulator in a detached shell launching the application
                in debug mode (port 9999).
    debug-run   starts the simulator in foreground launching the application
                in debug mode (port 9999).
    status      show the current status the simulator (started or stopped).
    send        send a message from an xml file
```

#### Sending a message from command line
With the command ./sim send-msg 'filename' is possible to send a message contained in a xml file.
The destination is the same configured for cise-sim and message sent and ack received will be stored in the cise-sim repository directory


## As a Docker container
To create and run the container we suggest using a simple docker compose file, where map the server ports and the volumes. For example :  
```
version: '3'
services:
  cise-sim:
    image: jrc-ispra/cise-sim:latest
    volumes:
      - /my/installation/path/conf:/srv/cise-simulator/conf
      - /my/installation/path/logs:/srv/cise-simulator/logs
      - /my/installation/path/msghistory:/srv/cise-simulator/msghistory
    ports:
      - 8280:8200
      - 8201:8201
``` 

To startup the docker container, open a terminal in the same directory where compose file is and execute the docker compose command :
```
docker-compose up -d
```
You can use the one provided with the distribution, but remember to modify the **/my/installation/path/** with the path used in the host machine

# Message History

Sent/Received CISE messages are stored in the `msghistory` folder by default. 

Each message is stored in a single XML file, whose filename follows this pattern:  
```
Timestamp_TypeName_Direction_Uuid
```

|Parameter|Description|
|---|---|
|Timestamp|Timestamp when the message was processed,following the format : yyyyMMdd-HHmmssSSS
|TypeName|Type of the message (i.e. PULLREQUEST, FORWARD, etc.) For the Acknowledge messages, SYNC are the ones received/sent synchronously after the message was sent/received
|Direction|RECV for message received, or SENT for message sent
|Uuid|Unique identifier (UUID)

Examples:  
`20200611-120029798_PULLREQUEST_SENT_31fb100d-dd13-450d-858b-d410a5f2c345`

`20200611-120029808_ACKSYNCH_RECV_184e0b37-bdb0-4efd-b993-ac18abd1f7ec`
