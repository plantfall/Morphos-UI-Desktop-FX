@echo off
echo Checking requirements to create the installer...

REM Application configuration
set "APP_NAME=MyApp"
set "APP_VERSION=1.1"
set "APP_VENDOR=Your Name"
set "APP_COPYRIGHT=Copyright 2024"
set "APP_DESCRIPTION=Your application description"
set "APP_MAIN_CLASS=my_app.App"
set "APP_ICON=..\src\main\resources\assets\app_ico.ico"

REM Check if jpackage is available
where jpackage >nul 2>nul
if %errorlevel% neq 0 (
    echo jpackage not found!
    echo Please install JDK 14 or higher.
    echo You can download JDK from: https://adoptium.net/
    pause
    exit /b 1
)

REM Check JDK version
for /f "tokens=3" %%a in ('java -version 2^>^&1 ^| findstr /i "version"') do set "java_version=%%a"
set "java_version=%java_version:~1,2%"
if %java_version% lss 14 (
    echo JDK version too old! Please install JDK 14 or higher.
    echo Current version: %java_version%
    pause
    exit /b 1
)

REM Check if WiX Toolset is installed
set "WIX_PATH="
for %%d in (
    "C:\Program Files (x86)\WiX Toolset v3.14\bin"
    "C:\Program Files\WiX Toolset v3.14\bin"
    "C:\Program Files (x86)\WiX Toolset v3.11\bin"
    "C:\Program Files\WiX Toolset v3.11\bin"
) do (
    if exist "%%~d\light.exe" (
        set "WIX_PATH=%%~d"
        goto :wix_found
    )
)

:wix_not_found
echo WiX Toolset not found!
echo.
echo Please follow these steps:
echo 1. Check if WiX Toolset was installed correctly
echo 2. Manually add the installation directory to system PATH
echo 3. Close and reopen the command prompt
echo 4. Run this script again
pause
exit /b 1

:wix_found
echo WiX Toolset found at: %WIX_PATH%
set "PATH=%WIX_PATH%;%PATH%"

echo All requirements met!
echo.
echo Creating installer...

REM Create directories
if not exist "build" mkdir build
if exist "build\installer" rmdir /s /q "build\installer"
if exist "build\MyApp" rmdir /s /q "build\MyApp"
if exist "..\dist" rmdir /s /q "..\dist"
mkdir build\installer
mkdir build\installer\bin
mkdir ..\dist

REM Copy JAR and JavaFX
set "JAR_FILE=my_app-%APP_VERSION%-jar-with-dependencies.jar"
copy "..\target\%JAR_FILE%" build\installer\
xcopy /E /I /Y "..\.java_fx\lib" "build\installer\lib"
xcopy /E /I /Y "..\.java_fx\bin\*.dll" "build\installer\bin"

REM Create runtime image with JavaFX
echo Creating runtime image...
jlink ^
    --module-path "%JAVA_HOME%\jmods;build\installer\lib" ^
    --add-modules javafx.controls,javafx.fxml,javafx.graphics,java.sql ^
    --output build\runtime

REM Copy JavaFX DLLs to runtime
echo Copying JavaFX DLLs to runtime...
xcopy /E /I /Y "..\.java_fx\bin\*.dll" "build\runtime\bin"

REM Run jpackage
echo Creating Windows installer...
jpackage ^
    --input build\installer ^
    --dest ..\dist ^
    --main-jar "%JAR_FILE%" ^
    --main-class %APP_MAIN_CLASS% ^
    --name %APP_NAME% ^
    --app-version %APP_VERSION% ^
    --vendor "%APP_VENDOR%" ^
    --copyright "%APP_COPYRIGHT%" ^
    --description "%APP_DESCRIPTION%" ^
    --type app-image ^
    --runtime-image build\runtime ^
    --java-options "-Djavafx.verbose=true" ^
    --java-options "-Dprism.verbose=true" ^
    --java-options "-Dprism.order=sw" ^
    --java-options "-Djava.library.path=bin" ^
    --icon "%APP_ICON%"

if %errorlevel% neq 0 (
    echo.
    echo An error occurred while creating the application image.
    pause
    exit /b 1
)

REM Copy JavaFX DLLs to the correct directory
echo Copying JavaFX DLLs...
xcopy /E /I /Y "..\.java_fx\bin\*.dll" "..\dist\%APP_NAME%\bin\"

echo.
echo Application image created successfully!
echo Creating MSI installer...

jpackage ^
    --app-image ..\dist\%APP_NAME% ^
    --name %APP_NAME% ^
    --app-version %APP_VERSION% ^
    --vendor "%APP_VENDOR%" ^
    --copyright "%APP_COPYRIGHT%" ^
    --description "%APP_DESCRIPTION%" ^
    --type msi ^
    --win-menu ^
    --win-menu-group "%APP_NAME%" ^
    --win-shortcut ^
    --win-dir-chooser ^
    --win-per-user-install ^
    --icon "%APP_ICON%"

if %errorlevel% neq 0 (
    echo.
    echo An error occurred while creating the installer.
    pause
    exit /b 1
)

REM Move MSI file to dist folder
echo Moving installer to dist folder...
move /Y %APP_NAME%-%APP_VERSION%.msi ..\dist\

echo.
echo Installer created successfully!
echo The installer file is at: ..\dist\%APP_NAME%-%APP_VERSION%.msi
echo.

REM Clean temporary directories
rmdir /s /q build\installer
rmdir /s /q build\runtime

pause 