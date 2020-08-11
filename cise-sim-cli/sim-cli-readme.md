# CISE Sim Command Line (CLI)

**CISE Sim CLI** is an application capable of sending and receiving CISE messages to/from CISE Nodes, adaptors or other CISE Sims. The CISE Sim CLI is conformant to the CISE Service model.

## Functionalities
- send CISE messages using a template 
- receive CISE messages
- validate the CISE messages according to the CISE Data and Service models.
- store sent/received messages


##Usage:
 ```
java -jar cise-sim-cli.jar <command line parmeters>
```
##Command Line parameters
|Options|Description|Default
|---|---|---|
|--config, -c|sim.property file path to configure the simulator| .
|--correlation-id, -r|Overrides the correlation id of the message to be sent||
|--debug, -d|Debug mode|false|
|--file, -f|File of the CISE message to be sent||
|--help, -h|Show help (this information)||
|--listen, -l|Set this argument to receive messages. The default port is 9999. Tospecify the port use the -p option|false|
|--message-id, -i|Overrides the message id of the message to be sent|f2b707e5-583a-460d-90cb-cef70891dc66|
|--port, -p|File of the CISE message to be sent|9999|
|--requires-ack, -a|Set to true the requiredAck field in the XML of the message|false|
|-log, -verbose|Level of verbosity|false|

