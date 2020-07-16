#!/bin/bash
cd target
mkdir dist
cd dist

cp ../cise-sim-1.3.0-ALPHA-cli.tar.gz ./
cp ../cise-sim-1.3.0-ALPHA.tar.gz ./
cp ../../README.md ./
cp ../../cise-sim-cli/sim-cli-readme.md ./

cp ../../docker-compose.yml .
echo 'docker load < docker_cisesim_latest.tar.gz' > docker_install.sh
chmod +x ./docker_install.sh
docker save ec-jrc/cise-sim:latest | gzip > ./docker_cisesim_latest.tar.gz

tar -cvf ../cise-sim-distribution.tar *
cd ..
rm -rf dist
cd ..
