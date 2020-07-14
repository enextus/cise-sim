#!/bin/bash
cd target
mkdir dist
cd dist
mkdir cise-sim-docker
mkdir cise-sim-targz
mkdir cise-cli-targz

cp ../cise-sim-1.3.0-ALPHA-cli.tar.gz cise-cli-targz/.
cp ../cise-sim-1.3.0-ALPHA.tar.gz cise-sim-targz
cp ../../README.md .
cp ../../cise-sim-cli/sim-cli-readme.md .

cp ../../docker-compose.yml cise-sim-docker/.
echo 'docker load < docker_cisesim_latest.tar.gz' > cise-sim-docker/install.sh
chmod +x cise-sim-docker/install.sh
docker save ec-jrc/cise-sim:latest | gzip > cise-sim-docker/docker_cisesim_latest.tar.gz

tar -cvf ../cise-sim-distribution.tar *
cd ..
rm -rf dist
cd ..
