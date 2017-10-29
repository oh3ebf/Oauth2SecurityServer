#!/bin/bash

export LD_LIBRARY_PATH=/usr/local/java/jre/lib/i386
export PATH=$PATH:/usr/local/java/bin

PROGRAM_NAME=SpringSecurityOauthServer
PID_FILE=/var/run/${PROGRAM_NAME}.pid

if [ $# -lt 1 ]; then
  echo 1>&2 "$0: not enough arguments"
  echo "Usage: ${PROGRAM_NAME}.sh [ start | stop ]"
  exit 2
elif [ $# -gt 1 ]; then
  echo 1>&2 "$0: too many arguments"
  echo "Usage: ${PROGRAM_NAME}.sh [ start | stop ]"
  exit 2
fi

case $1 in
  "start") 
    # check if pid file exist
    if [ -e "${PID_FILE}" ]; then 
      echo "Program is already running..."
    else
      echo "Starting ${PROGRAM_NAME}..."
      java -jar ${PROGRAM_NAME}.jar &
      sleep 2
      # create pid file
      echo `ps -elf | grep ${PROGRAM_NAME}.jar | grep -v grep | awk '{print $4}'` > /var/run/${PROGRAM_NAME}.pid
    fi  
    ;;
  "stop") 
      # check if pid file exist
      if [ -e "${PID_FILE}" ]; then 
        echo "Stopping ${PROGRAM_NAME}..."
        kill `cat /var/run/${PROGRAM_NAME}.pid`
      fi
    ;;
esac








