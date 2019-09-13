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

copy the jks file to local directory
~~~bash
mkdir /opt/jboss/EuciseData/sim-egn/conf -p
cp /cise-emulator-assembly/src/main/conf/keyStore.jks /opt/jboss/EuciseData/sim-egn/conf/apache-nodecx.jks
~~~
#### start the java api 
~~~bash
  java -jar ./cise-emulator-app/target/cise-emulator-app-1.1-SNAPSHOT-web.jar  server ./cise-emulator-assembly/src/main/conf/config.yml &
~~~

#### init the nodejs server 
in that part we assume the npm / nodejs tool and framework are installed in lts version (see https://webgate.ec.europa.eu/CITnet/confluence/display/OCNET/Ubuntu+Workstations)
~~~bash
   cd cise-emulator-react/
   npm install
   npm run build --scripts-prepend-node-path=auto  
   ~~~
#### start the nodejs server 
~~~bash
   npm run start --scripts-prepend-node-path=auto  
~~~