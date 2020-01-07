FROM 	   	docker.sterlite.com:5043/centos:v1 
MAINTAINER 	shivani.kothari@sterlite.com

WORKDIR 	/opt/crestelsetup
ENV 		HOME /opt/crestelsetup
ENV 		CRESTELSETUP_HOME /opt/crestelsetup
ENV 		ANT_HOME /opt/crestelsetup/apache-ant-1.8.1
ENV 		PATH $ANT_HOME/bin:$PATH
ENV		MAVEN_HOME /opt/crestelsetup/apache-maven-3.3.9
ENV             PATH $MAVEN_HOME/bin:$PATH
ENV 		TZ Asia/Kolkata
ENV             JAVA_HOME /opt/crestelsetup/jdk1.8.0_65
ARG             ENTRYPOINT=crestelcrm_frontend_entry.sh
ENV             ENTRYPOINT  ${ENTRYPOINT}

ADD 		jdk1.8.0_65/ /opt/crestelsetup/jdk1.8.0_65

COPY 		./.bash_profile ./.bashrc /root/
COPY            ./.bash_profile ./.bashrc $ENTRYPOINT /opt/crestelsetup/

ADD              packages /opt/crestelsetup/packages
ADD              hazelcast.xml checkserverstatus.sh getsecret.py  /opt/crestelsetup/
ADD		 apache-tomcat-8.5.11 /opt/crestelsetup/apache-tomcat-8.5.11/
RUN 		 cd /opt/crestelsetup/packages ; rpm -Uvh *.rpm ; cd py-packages ; pip install --no-index --find-links . -r requirements.txt ; rm -rf /opt/crestelsetup/packages

ENTRYPOINT [ "/bin/bash", "-c" , "sh ${ENTRYPOINT}" ]
