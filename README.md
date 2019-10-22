# CISE Sim

**CISE Sim** is an application capable to send and receive messages conform to the CISE Protocol.
CISE SIim is able to simulate the behavior both of a CISE Node and of a CISE Adaptor. It is able to:
- send CISE messages using a template as a a base xml message and automatically creating XML fields like messageId, correlationId, creation date and signature needed for the message to be accepted by the receiveing endpoint. 
- receive messages answering with synchronous acknowledgements. The CISE sync ack are supporiting the success case and a subset of the errors that can be reported by a CISE node (i.e access right matrix errors are not supported)  

## Requirements
The application must be run on a GNU/Linux operative system and requires to have installed the following software:
- Java 1.8

## How to run CISE Sim
CISE Sim is distributed in a tar.gz archive, therefore it needs to be unpacked in a folder by launching the following commands from the UNIX shell:

```bash
...$ mkdir -p /my/installation/path 
...$ tar -xvzf cise-sim-*.tar.gz -C /my/installation/path --strip-components=1
...$ cd /my/installation/path
```
After unpacking the archive, you can use a provided shell script to launch the application in foreground:

```bash
...$ cd /my/installation/path
...$ ./sim run
```

You can also run the application in background  with the command:

```bash
...$ ./sim start
```

and then stop it:
```bash
...$ ./sim stop
```

## Configuration 
Before using the application to test the CISE node or a CISE adaptor, the application needs to be configured accordingly to the specific required usage.

The ``conf/`` directory contains two configuration files:

- ``sim.properties``: is a property file containing the parameters relative to the participant, service and signature configuration.
- ``config.yml``: is a YAML file able to configure the application server used to run the CISE Sim application. Here you may find mainly the configuration of the port the CISE Sim will use to listen for incoming messages (**default is 8080**) and the logging configuration.

