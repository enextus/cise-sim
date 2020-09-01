#!/bin/bash
docker save ec-jrc/cise-sim:"${project.version}" | gzip > ./docker_cisesim_"${project.version}".tar.gz
