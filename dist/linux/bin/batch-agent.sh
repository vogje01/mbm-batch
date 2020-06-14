#!/bin/bash -x
#
# Momentum Batch Management agent startup script.
#
# Usage:
#     batch-agent.sh [start|stop|restart] <nodeName>
#
# nodeName can be anything, for instance 01, 02, prod etc. The node name is only used internally
# to route messages and commands between the server and the agents.
#

# Module
MODULE=batch-agent

# Version
VERSION=0.0.4

# Service name
SERVICE_NAME=$MODULE-$2
PID_PATH_NAME=/tmp/$MODULE-$2-pid

# Base directory
BASEDIR=/opt/batch/agent

# Directories
LIB_DIR=$BASEDIR/lib
LOG_DIR=$BASEDIR/log
ETC_DIR=$BASEDIR/etc

# Configuration
CONFIGURATION=$ETC_DIR/$MODULE-$2.yml

# Log configuration
LOG_CONFIGURATION=$ETC_DIR/$MODULE-$2-log.yml

# Node name
JAVA_ARGUMENTS="-Dagent.loggingDirectory=$LOG_DIR -Dlog4j2.configurationFile=file:$LOG_CONFIGURATION -Dspring.config.location=file:$CONFIGURATION"

# Executable JAR
EXECUTABLE=$MODULE-$VERSION.jar

# Which java to use
if [ -z "$JAVA_HOME" ]; then
  JAVA="java"
else
  JAVA="$JAVA_HOME/bin/java"
fi

cd $BASEDIR
case $1 in
start)
  echo "Starting $SERVICE_NAME ..."
  if [ ! -f $PID_PATH_NAME ]; then
    nohup $JAVA $JAVA_ARGUMENTS -jar $LIB_DIR/$EXECUTABLE >$LOG_DIR/$MODULE-$2.log 2>&1 &
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
    nohup $JAVA $JAVA_ARGUMENTS -jar $LIB_DIR/$EXECUTABLE >$LOG_DIR/$MODULE-$2.log 2>&1 &
    echo $! >$PID_PATH_NAME
    echo "$SERVICE_NAME started ..."
  else
    echo "$SERVICE_NAME is not running ..."
  fi
  ;;
esac
