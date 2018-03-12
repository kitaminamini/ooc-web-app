#!/bin/bash

mvn clean
mvn package
sudo iptables -A PREROUTING -t nat -p tcp --dport 80 -j REDIRECT --to-ports 8082
java -jar target/login-webapp-1.0-SNAPSHOT.jar
