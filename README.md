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

## Deployment
With the distribution package (tar.gz file) the following simple procedure can be used:

```bash
mkdir /my/installation/path -p
tar -xvzf cise-sim-*.tar.gz -C /my/installation/path --strip-components=1
```

## Configuration 
Before running the distribution package must be configured accordingly to your specific needs.

In _conf/_ directory you can find:
- _sim.properties_ file that contains parameters relative to messages and signature
```bash
...$ cd /my/installation/path
...$ vim conf/sim.properties
```
- _config.yml_ file that contains parameters relative to simulator server 

All the parameters are depicted inside the files.

## Execution
After you have configured all the parameters you can run the application:
```bash
cd /my/installation/path
./sim run
```
You can also run the application as a daemon with the command:
```bash
./sim start
```
and you can stop it with:
```bash
./sim stop
```
