# CISE emu
The project consist of the following component 

## how to compile
...
## how to execute
### command line interface 
~/cise-emulator would be the repo directory
#### start the server 
~~~bash
  java -jar ./cise-emulator-app/target/cise-emulator-app-1.1-SNAPSHOT-cli.jar cliserver
~~~
#### start the client
~~~bash
  java -jar ./cise-emulator-app/target/cise-emulator-app-1.1-SNAPSHOT-cli.jar  sender -c ./cise-emulator-assembly/src/main/conf/cliconfig.yml -s ./cise-emulator-assembly/src/main/conf/xmlmessages/PushTemplate.xml
~~~
### web interface
#### start the server 
~~~bash
  java -jar ./cise-emulator-app/target/cise-emulator-app-1.1-SNAPSHOT-web.jar server 
~~~