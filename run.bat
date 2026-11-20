@echo off
rem Usage: run.bat [all^|list^|dates^|xml^|bonus^|token^|csv]
pushd "%~dp0"
java -cp out lab.employee.Main %*
popd
