FROM tomcat:latest

MAINTAINER gregtech87

#COPY /target/Pams-Backend.war.original /usr/local/tomcat/webapps/ROOT.war.original
#RUN rm /usr/local/tomcat/conf/tomcat-users.xml
#COPY /tomcat/tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
#COPY /tomcat/docs /usr/local/tomcat/webapps/docs
#COPY /tomcat/examples /usr/local/tomcat/webapps/examples
#COPY /tomcat/host-manager /usr/local/tomcat/webapps/host-manager
#COPY /tomcat/manager /usr/local/tomcat/webapps/manager
#COPY /target/pamgui /usr/local/tomcat/webapps/pamgui

COPY /Pams-Backend.war /usr/local/tomcat/webapps/ROOT.war
#COPY /pam-gui /usr/local/tomcat/webapps/ROOT

EXPOSE 8080
#EXPOSE 8586

