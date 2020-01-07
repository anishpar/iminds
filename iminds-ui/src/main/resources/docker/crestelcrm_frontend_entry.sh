#HOST ENTRY
TOMCAT_HOME=/opt/crestelsetup/apache-tomcat-8.5.11

#OWNERSHIP:
chmod -R 755 /opt/crestelsetup/

echo "JAVA_HOME=/opt/crestelsetup/jdk1.8.0_65 ; export JAVA_HOME" >> /opt/crestelsetup/.bash_profile
echo "PATH=$JAVA_HOME/bin:$PATH" >> /opt/crestelsetup/.bash_profile
source /opt/crestelsetup/.bash_profile

echo "JAVA_HOME=/opt/crestelsetup/jdk1.8.0_65 ; export JAVA_HOME" >> /root/.bash_profile
echo "PATH=$JAVA_HOME/bin:$PATH" >> /root/.bash_profile
source /root/.bash_profile

#CONFIGURE FRONTEND:

crestelcrm_frontend_ip=`hostname -i`

echo "$crestelcrm_backend_service_ip ${crestelcrm_backend_web_url}" | tee -a /etc/hosts
echo "$crestelcrm_frontend_ip ${crestelcrm_frontend_web_url}" | tee -a /etc/hosts
echo "$crestelbrm_service_ip $crestelrest_url" | tee -a /etc/hosts

#STARTUP:
sed -ri "s@address=[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}@address=${crestelcrm_frontend_ip}@g" $TOMCAT_HOME/bin/catalina.sh

#CONFIGURE WEB-URL:
sed -ri "s@<Host name=\"([aA-zZ0-9.]*)\"( *)appBase=\"webapps\"( *)unpackWARs=\"true\"( *)autoDeploy=\"true\">@<Host name=\"${crestelcrm_frontend_web_url}\" appBase=\"webapps\"  unpackWARs=\"true\" autoDeploy=\"true\">@g" $TOMCAT_HOME/conf/server.xml

sed -ri "0,/<\/Host>;/s/<\/Host>/<Context path=\"\" docBase=\"crm\" debug=\"0\" reloadable=\"true\" crossContext=\"true\" \/>\n<\/Host>/I"  $TOMCAT_HOME/conf/server.xml	
	
#UNPACK WAR:
cd $TOMCAT_HOME/webapps
tar -xvzf crmui.tar.gz

if [ $ENABLE_CWSC = "true" ];
then
        mv dist cwsc
        sed -i "s~isCWSC:\!1~isCWSC:1~g"  cwsc/main.*.bundle.js
        sed -i 's~<\!\-\-<link href="assets/cwsc/style.css" rel="stylesheet">\-\->~<link href="assets/cwsc/style.css" rel="stylesheet">~g' cwsc/index.html
else
        mv dist crm
fi

#URL CONFIG:
mkdir -p $TOMCAT_HOME/webapps/stlconfig
                echo "{
                        \"api_host\" : \"http://$crestelcrm_backend_web_url:$crestelcrm_backend_service_port/CRMIntegration/\",
                        \"config\" : {
                        }
                }" >> $TOMCAT_HOME/webapps/stlconfig/config.json


#SSH RELATED CONFIGURATION:

echo y | ssh-keygen -t rsa -f /etc/ssh/ssh_host_rsa_key -N '' ; \
echo y |ssh-keygen -t dsa -f /etc/ssh/ssh_host_dsa_key -N '' ; \
echo "export VISIBLE=now" >> /etc/profile ; \
ssh-keygen -f /etc/ssh/ssh_host_ecdsa_key -N '' -t ecdsa ; \
ssh-keygen -t ed25519 -f /etc/ssh/ssh_host_ed25519_key -N '' ; \
sed -ri "s@^GSSAPIAuthentication(.*)@GSSAPIAuthentication no@" /etc/ssh/sshd_config ; \
sed -ri "s@^#UseDNS(.*)@UseDNS no@" /etc/ssh/sshd_config ; \
sed -i 's@session\s*required\s*pam_loginuid.so@session optional pam_loginuid.so@g' /etc/pam.d/sshd ; \
chmod 755 /var/empty/sshd
chown root:root /var/empty/sshd

/usr/sbin/sshd &
echo "root:crestel" | chpasswd

#APPLICATION STARTUP:
cd
echo "cd $TOMCAT_HOME/bin ;
sh shutdown.sh ;
rm -rf ../logs/* ../work/* ../temp/*" >> cleanup.sh

rm -rf /opt/crestelsetup/apache-ant-1.8.1
chmod +x $TOMCAT_HOME/bin/*.sh

mkdir -p /opt/crestelsetup/MSO /opt/crestelsetup/statementOfAccountCSVs $TOMCAT_HOME/logs

#User Creation Applicaiton startup:
useradd -d "/opt/crestelsetup" $username

chown -R $username:$groupname /opt/crestelsetup/
echo $username:$password | chpasswd

su -m "$username" -c "export UMASK='0022' ; cd /opt/crestelsetup/apache-tomcat-8.5.11/bin ; sh startup.sh ; echo $UMASK"

tail -f /dev/null
