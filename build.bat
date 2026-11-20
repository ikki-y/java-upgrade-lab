@echo off
rem Build with Maven. Run C:\java\java21.bat first.
rem Compiled classes go to target\classes, libraries to target\lib.
pushd "%~dp0"
call mvn package
if errorlevel 1 (
  echo BUILD FAILED
  popd
  exit /b 1
)
echo BUILD OK
popd
