#!/usr/bin/env sh

docker run -d -p '5454:5432' -e POSTGRES_Db=nightjar-postgres -e POSTGRES_USER=nightjar -e POSTGRES_PASSWORD=nightjar --name nightjar-db postgres:10.13-alpine
