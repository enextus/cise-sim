#!/bin/bash
docker save ec-jrc/cise-sim:1.3.0 | gzip > ./docker_cisesim_1.3.0.tar.gz
