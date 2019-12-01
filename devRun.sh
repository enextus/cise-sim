cd ./target
tar xzvf cise-sim-1.1-beta.tar.gz
cd ./cise-sim-1.1-beta
sed  -i 's/your-endpoint\.url\.cise/localhost/g' ./conf/sim.properties
./sim run
