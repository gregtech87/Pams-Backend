FROM tomcat:latest

MAINTAINER gregtech87


COPY /Pams-Backend.war /usr/local/tomcat/webapps/ROOT.war


EXPOSE 8080

