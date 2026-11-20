@echo off
rem Build with the JDK currently on PATH.
rem Run C:\java\java8.bat or C:\java\java21.bat first.
pushd "%~dp0"
java -version
if exist out rmdir /s /q out
mkdir out
dir /s /b src\*.java > sources.txt
javac -encoding UTF-8 -d out @sources.txt
if errorlevel 1 (
  echo BUILD FAILED
  popd
  exit /b 1
)
echo BUILD OK
popd
