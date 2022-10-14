#!/usr/bin/env bash
docker rm $(docker ps -a -q) -f
docker container prune
docker volume prune
docker network prune
docker images prune