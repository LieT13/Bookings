#!/bin/bash

TOMCAT8_LIB_DIR=/var/lib/tomcat8

echo "Building BookingManager..."
mvn clean install

echo "Deploying BookingManager"

sudo service tomcat8 stop
sudo rm -rf /var/lib/tomcat8/webapps/BookingManager
sudo rm /var/lib/tomcat8/webapps/BookingManager.war
sudo cp ./bm-rest/target/BookingManager.war $TOMCAT8_LIB_DIR/webapps/BookingManager.war
sudo service tomcat8 start

echo "Done!"
