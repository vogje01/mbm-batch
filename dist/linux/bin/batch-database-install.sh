#!/bin/bash
#
# Momentum Batch Management database startup script.
#
# Usage:
#     batch-database-install.sh drop|create|createAdmin|install|update|encrypt
#
#

# Module
MODULE=batch-database-install

# Version
VERSION=0.0.6-SNAPSHOT

# Base directory
BASEDIR=/C/work/batch/server

# Directories
LIB_DIR=$BASEDIR/lib
LOG_DIR=$BASEDIR/log
ETC_DIR=$BASEDIR/etc

# Configuration
CONFIGURATION=$ETC_DIR/$MODULE.yml

# Log configuration
LOG_CONFIGURATION=$ETC_DIR/$MODULE-log.yml

# Jasypt password
JASYPT_PASSWORD=1Qaz0oKm

# MySQL database password
MYSQL_ROOT_PASSWORD=Secret_123

# Node name
JAVA_ARGUMENTS="-Ddatabase.loggingDirectory=$LOG_DIR -Dlogging.config=file:$LOG_CONFIGURATION -Dspring.config.location=file:$CONFIGURATION -Djasypt.encryptor.password=$JASYPT_PASSWORD"

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
drop)
  echo "Dropping batch database ..."
  nohup $JAVA $JAVA_ARGUMENTS -jar ./lib/$EXECUTABLE drop -u root -p $MYSQL_ROOT_PASSWORD -d jdbc:mysql://localhost:3306 >$LOG_DIR/$MODULE.log 2>&1
  echo "Database batch dropped ..."
  ;;
create)
  echo "Creating batch database ..."
  nohup $JAVA $JAVA_ARGUMENTS -jar ./lib/$EXECUTABLE create -u root -p $MYSQL_ROOT_PASSWORD -d jdbc:mysql://localhost:3306 >$LOG_DIR/$MODULE.log 2>&1
  echo "Database batch created ..."
  ;;
createAdmin)
  echo "Creating batch admin user ..."
  nohup $JAVA $JAVA_ARGUMENTS -jar ./lib/$EXECUTABLE createAdmin -u root -p $MYSQL_ROOT_PASSWORD -d jdbc:mysql://localhost:3306 >$LOG_DIR/$MODULE.log 2>&1
  echo "Admin user batch created ..."
  ;;
install)
  echo "Installing batch database schema..."
  nohup $JAVA $JAVA_ARGUMENTS -jar ./lib/$EXECUTABLE install -u admin -p $MYSQL_ROOT_PASSWORD -d jdbc:mysql://localhost:3306/batch >$LOG_DIR/$MODULE.log 2>&1
  echo "Database schema batch installed ..."
  ;;
update)
  echo "Updating batch database schema..."
  nohup $JAVA $JAVA_ARGUMENTS -jar ./lib/$EXECUTABLE update -u admin -p $MYSQL_ROOT_PASSWORD -d jdbc:mysql://localhost:3306/batch >$LOG_DIR/$MODULE.log 2>&1
  echo "Database schema batch updated ..."
  ;;
encrypt)
  echo "Encrypting password..."
  nohup $JAVA $JAVA_ARGUMENTS -jar ./lib/$EXECUTABLE encrypt -p $MYSQL_ROOT_PASSWORD >$LOG_DIR/$MODULE.log 2>&1
  echo "Password encrypted ..."
  ;;
*)
  echo "Usage: "
  echo "   batch-database.sh drop|create|createAdmin|install|update|encrypt"
  ;;
esac
