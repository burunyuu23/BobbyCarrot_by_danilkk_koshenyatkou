@echo off

set CD=%~dp0

rem ����� 㪠����, ��� ����� JDK, �᫨ ��� �⮣� �� ��室���� ������� java
::set JAVA_HOME=C:\Users\zEzzLike\.jdks\openjdk-16.0.2\bin;%PATH%

set JAVAW=javaw
if not "%JAVA_HOME%"=="" (
  set JAVAW="%JAVA_HOME%\bin\%JAVAW%"
)

%JAVAW% -classpath "%CD%\out\production\GameTemplate" ru.vsu.cs.course1.game.Program %*
