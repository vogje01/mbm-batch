@ECHO OFF

::----------------------------------------------------------------------
:: MBM database installation script
::----------------------------------------------------------------------
PUSHD %CD%

:: ---------------------------------------------------------------------
:: Ensure MBM_HOME points to the directory where MBM is installed.
:: ---------------------------------------------------------------------
SET MBM_HOME_DIR=%~dp0..
cd %MBM_HOME_DIR%

:: ---------------------------------------------------------------------
:: Ensure JDK is installed
:: ---------------------------------------------------------------------
IF EXIST "%JAVA_HOME%" SET JDK=%JAVA_HOME%
SET JAVA_EXE=%JDK%\bin\java.exe
IF NOT EXIST "%JAVA_EXE%" SET JAVA_EXE=%JDK%\jre\bin\java.exe
IF NOT EXIST "%JAVA_EXE%" (
  ECHO ERROR: cannot start MBM database installation.
  ECHO No JDK found. Please validate either JDK_HOME or JAVA_HOME points to valid JDK installation.
  EXIT /B
)

:: ---------------------------------------------------------------------
:: MBM settings
:: ---------------------------------------------------------------------
SET MODULE=batch-database-install
SET VERSION=0.0.6-RELEASE
SET LIB_DIR=%MBM_HOME_DIR%\lib
SET LOG_DIR=%MBM_HOME_DIR%\log
SET ETC_DIR=%MBM_HOME_DIR%\etc
SET CONFIGURATION=%ETC_DIR%\%MODULE%.yml
SET LOG_CONFIGURATION=%ETC_DIR%\%MODULE%-log.yml
SET JASYPT_PASSWORD=1Qaz0oKm
SET JAR_FILE=%LIB_DIR%\%MODULE%-%VERSION%.jar

:: ---------------------------------------------------------------------
:: JVM options
:: ---------------------------------------------------------------------
SET JAVA_ARGUMENTS=-Ddatabase.loggingDirectory=%LOG_DIR% -Dlogging.config=file:%LOG_CONFIGURATION% -Dspring.config.location=file:%CONFIGURATION% -Djasypt.encryptor.password=%JASYPT_PASSWORD%

:: ---------------------------------------------------------------------
:: Start installation
:: ---------------------------------------------------------------------
"%JAVA_EXE%" %JAVA_ARGUMENTS% -jar %JAR_FILE% %* 2> NUL

if %ERRORLEVEL% GEQ 1 EXIT /B 1

POPD
