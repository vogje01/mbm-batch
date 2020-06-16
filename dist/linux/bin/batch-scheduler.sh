#!/bin/bash
#
# Momentum Batch Management scheduler startup script.
#
# Usage:
#     batch-scheduler.sh start|stop|restart
#

# Module
MODULE=batch-scheduler

# Version
VERSION=0.0.4

# Service name
SERVICE_NAME=batch-scheduler
PID_PATH_NAME=/tmp/$MODULE-pid

# Base directory
BASEDIR=/opt/batch/server

# Directories
LIB_DIR=$BASEDIR/lib
LOG_DIR=$BASEDIR/log
ETC_DIR=$BASEDIR/etc

# Executable JAR
EXECUTABLE=$MODULE-$VERSION.jar

# Configuration
CONFIGURATION=$ETC_DIR/$MODULE.yml

# Logging configuration
LOG_CONFIGURATION=$ETC_DIR/$MODULE-log.yml

# Which java to use
if [ -z "$JAVA_HOME" ]; then
  JAVA="java"
else
  JAVA="$JAVA_HOME/bin/java"
fi

# Java arguments
JAVA_ARGUMENTS="-Dscheduler.loggingDirectory=$LOG_DIR -Dlogging.config=file:$LOG_CONFIGURATION -Dspring.config.location=file:$CONFIGURATION -Djasypt.encryptor.password=1Qaz0oKm"

cd $BASEDIR
case $1 in
start)
  echo "Starting $SERVICE_NAME ..."
  if [ ! -f $PID_PATH_NAME ]; then
    nohup $JAVA $JAVA_ARGUMENTS -jar $LIB_DIR/$EXECUTABLE >$LOG_DIR/$MODULE.log 2>&1 &
    echo $! >$PID_PATH_NAME
    echo "$SERVICE_NAME started ..."
  else
    echo "$SERVICE_NAME is already running ..."
  fi
  ;;
stop)
  if [ -f $PID_PATH_NAME ]; then
    PID=$(cat $PID_PATH_NAME)
    echo "$SERVICE_NAME stopping ..."
    kill $PID
    echo "$SERVICE_NAME stopped ..."
    rm $PID_PATH_NAME
  else
    echo "$SERVICE_NAME is not running ..."
  fi
  ;;
restart)
  if [ -f $PID_PATH_NAME ]; then
    PID=$(cat $PID_PATH_NAME)
    echo "$SERVICE_NAME stopping ..."
    kill $PID
    echo "$SERVICE_NAME stopped ..."
    rm $PID_PATH_NAME
    echo "$SERVICE_NAME starting ..."
    nohup $JAVA $JAVA_ARGUMENTS -jar $LIB_DIR/$EXECUTABLE >$LOG_DIR/$MODULE.log 2>&1 &
    echo $! >$PID_PATH_NAME
    echo "$SERVICE_NAME started ..."
  else
    echo "$SERVICE_NAME is not running ..."
  fi
  ;;
*)
  echo "Usage: "
  echo "   batch-scheduler.sh start|stop|restart"
  ;;
esac
