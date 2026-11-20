@echo off
rem Usage: run.bat [all^|list^|dates^|xml^|bonus^|token^|csv]
pushd "%~dp0"
java -cp "target\classes;target\lib\*" lab.employee.Main %*
popd
