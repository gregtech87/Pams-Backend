FROM tomcat:latest

MAINTAINER gregtech87

#   Prepare deployment
# Set correct mail and database in application.properties
# Build
# Maven Clean and package
# Copy from target to project directory
#
COPY target/Pams-Backend.war /usr/local/tomcat/webapps/ROOT.war
COPY /src/main/resources/pdfTemplateUserInfo /usr/local/tomcat/src/main/resources/pdfTemplateUserInfo
COPY /src/main/resources/static/index.html /usr/local/tomcat/webapps
COPY /src/main/resources/static/dribbble_1.gif /usr/local/tomcat/webapps
VOLUME /usr/local/tomcat/User-Files

EXPOSE 8080

