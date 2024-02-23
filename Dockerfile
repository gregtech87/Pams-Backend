FROM tomcat:latest

MAINTAINER gregtech87

#   Prepare deployment
# Edit email link Emailservice line 36, 37
# Set correct mail in application.properties
# Build
# Maven Clean and Install
# Copy from target to project directory
#
COPY /Pams-Backend.war /usr/local/tomcat/webapps/ROOT.war


EXPOSE 8080

