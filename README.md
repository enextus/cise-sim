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

The application sever will start showing an banner and some information in the log. The output should 
be similar to the following one:
```bash
$ ./sim run                                                                                                                      [18:10:17] 
CISE emulator
Java path:    /home/gicappa/.jabba/jdk/openjdk-shenandoah@1.8.0/bin/java
Java version: "1.8.0-builds.shipilev.net-openjdk-shenandoah-jdk8"

==============================================
INFO  [2019-10-22 16:10:23,386] io.dropwizard.server.ServerFactory: Starting EmulatorApp
     _______________ ______   _____ ______  ___
    / ____/  _/ ___// ____/  / ___//  _/  |/  /
   / /    / / \__ \/ __/     \__ \ / // /|_/ /
  / /____/ / ___/ / /___    ___/ // // /  / /
  \____/___//____/_____/   /____/___/_/  /_/    CISE Sim (c) 2019

INFO  [2019-10-22 16:10:23,437] org.eclipse.jetty.server.Server: jetty-9.4.z-SNAPSHOT; built: 2018-06-05T18:24:03.829Z; git: d5fc0523cfa96bfebfbda19606cad384d772f04c; jvm 1.8.0-builds.shipilev.net-openjdk-shenandoah-jdk8-b418-20190629-aarch64-shenandoah-jdk8u222-b06
INFO  [2019-10-22 16:10:23,767] io.dropwizard.jersey.DropwizardResourceConfig: The following paths were found for the configured resources:

    POST    /api/messages (eu.cise.emulator.api.resources.MessageResource)
    DELETE  /api/ui/messages/latest (eu.cise.emulator.api.resources.UiMessageResource)
    GET     /api/ui/service/self (eu.cise.emulator.api.resources.UiServiceResource)
    GET     /api/ui/templates (eu.cise.emulator.api.resources.TemplateResource)
    GET     /api/ui/templates/{templateId} (eu.cise.emulator.api.resources.TemplateResource)
    POST    /api/ui/templates/{templateId} (eu.cise.emulator.api.resources.TemplateResource)

INFO  [2019-10-22 16:10:23,770] io.dropwizard.setup.AdminEnvironment: tasks = 

    POST    /tasks/log-level (io.dropwizard.servlets.tasks.LogConfigurationTask)
    POST    /tasks/gc (io.dropwizard.servlets.tasks.GarbageCollectionTask)

INFO  [2019-10-22 16:10:23,778] org.eclipse.jetty.server.AbstractConnector: Started application@7752c0e7{HTTP/1.1,[http/1.1]}{0.0.0.0:8080}
INFO  [2019-10-22 16:10:23,779] org.eclipse.jetty.server.AbstractConnector: Started admin@77ba583{HTTP/1.1,[http/1.1]}{0.0.0.0:8081}
==============================================

INFO  [2019-10-22 16:10:23,779] org.eclipse.jetty.server.Server: Started @2196ms
``` 

**The CISE Sim web interface is now ready to be accessed by opening a browser to the address: http://localhost:8080/**. 
**The CISE Sim endpoint is ready to be contacted by a CISE Node or adaptor that can send messages to the endpoint url: ``http://localhost:8080/api/messages``.**

## Configuration 
Before connecting to a CISE node or adaptor, the application needs to be configured properly.

The ``conf/`` directory contains two configuration files:

- ``sim.properties``: is a property file 
- ``config.yml``: is a YAML file able to configure the application server used to run the CISE Sim application. Here you may find mainly the configuration of the port the CISE Sim will use to listen for incoming messages (**default is 8080**) and the logging configuration.

### sim.properties
This file is the main configuration file that allow to configure the parameters related to the participant information, 
the services and signature files.

**NOTE** The signature is a fundamental part of the communication in CISE. Therefore is important that, when the CISE Sim is 
used to send message to the node, it uses a Java Key Store file (aFileEndingWith.jks) generated by the node administrator.
To allow the CISESim to send message to the node, the node administrator needs to create a participant specifically for it. The node administrator should provide the java key store file with the private and public key of the CISE Sim providing also an alias to be set into the sim.properties file to the CISE Sim user in order to configure it. The keystore file should be substituted to the onw present in the ``conf`` directory.   

More specifically: 

| Parameter|Description|
|---|---|
|service.participantid| is the participant id that must be configured into the node when accessing it and that must be referred by the public and private key generated by the node administrator to allow the CISE sim to access the node. In the case of an adaptor developer this configuration is not impacting the functioning of the CISE Sim|
|destination.endpoint-url|is the URL of the CISE Node or of the CISE Adaptor that will receive the messages sent by the CISE Sim|
|template.messages.directory|is a directory path relative to the directory where the CISE Sim is installed that specify where to find the templates that will be loaded in the dropdown field present in the web interface. *WARNING* at the moment the path is accepted only relatively to the sim directory in the future other also absolute path will bw supported.|
|signature.keyStoreFileName|is the filename of the keystore. The `String` parameter may refer to a file in the classpath or to file in the filesystem. Examples: a file in the root of the classpath will referred like this: ``.withKeyStoreName("in-the-root.txt)`` while a file in the filesystem will be referred like this: ``.withKeyStoreName("/home/myuser/in-the-root.txt)``|
|signature.keyStorePassword|is the password to access the keystore|
|signature.privateKeyAlias|is the alias that identify the _key pair_ in the keystore that will be used to sign the CISE message. Example: ``sim1-node01.node01.eucise.fr`` may find the key pair existing in the keystore associated to the sender id used to sign the cise message.|
|signature.privateKeyPassword|is the password to access the key pair|

```properties
################################################################
# CISE sim properties file
#
# Version 1.1 Beta
###############################################################

# The participant ID of simulator instance
service.participantid=cisesim1-nodecx.nodecx.eucise.cx

# The destination to which the simulator sends the messages
destination.endpoint-url=http://localhost:8080/api/messages

# Directory relative to installation where the server looks for message templates
template.messages.directory=templates/messages

# Signature configuration
signature.keyStoreFileName=keyStore.jks
signature.keyStorePassword=password
signature.privateKeyAlias=apache.nodecx.eucise.cx
signature.privateKeyPassword=password
```
