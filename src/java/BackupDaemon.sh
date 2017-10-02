#!/bin/bash

#Questo job va messo nel cron tab
# ogni tre ore: 0 */3 * * *
#ogni giorno all'una di pomeriggio : 0 13 * * *

DATE=`date +%Y-%m-%d:%H:%M:%S`

mysqldump -u root -p admin -h localhost Progetto > /opt/tomcat/webapps/Gestionale/Backups/`date +%Y-%m-%d:%H:%M:%S`.sql


