#!/usr/bin/env sh
echo "Iniciando Proyecto Demostración OpenFaaS con java"
echo "Subiendo la base de datos"
docker run --name mysql-openfaas --rm -it -d -e MYSQL_ROOT_PASSWORD=12345678 -e MYSQL_DATABASE=openfaas -p 3306:3306 mysql:5.7.32
faas-cli template pull
faas-cli deploy -f demo-java-openfaas.yml
echo "Demo Disponible"