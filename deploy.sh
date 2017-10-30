#!/bin/bash

TOMCAT8_LIB_DIR=/var/lib/tomcat8

echo "Building Bookings..."
mvn clean install

echo "Deploying Bookings"

sudo service tomcat8 stop
sudo rm -rf /var/lib/tomcat8/webapps/Bookings
sudo rm /var/lib/tomcat8/webapps/Bookings.war
sudo cp ./bm-rest/target/Bookings.war $TOMCAT8_LIB_DIR/webapps/Bookings.war
sudo service tomcat8 start

echo "Done!"
