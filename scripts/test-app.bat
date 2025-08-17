@echo off
echo Testing the application...

REM Application configuration
set "APP_NAME=MyApp"

REM Check if the application image exists
if not exist "..\dist\%APP_NAME%" (
    echo Application image not found!
    echo Run create-installer.bat first to create the image.
    pause
    exit /b 1
)

REM Check Java environment
echo Checking Java environment...
java -version
if %errorlevel% neq 0 (
    echo Java not found or has issues!
    pause
    exit /b 1
)

REM Check directory structure
echo.
echo Checking directory structure...
dir "..\dist\%APP_NAME%"
echo.

REM Run the application and capture error log
echo Starting the application...
echo Error output will be saved to error.log
"..\dist\%APP_NAME%\%APP_NAME%.exe" 2> error.log

REM Check for errors
if %errorlevel% neq 0 (
    echo.
    echo An error occurred while running the application.
    echo Error details:
    type error.log
    echo.
    echo Complete log was saved to error.log
    pause
    exit /b 1
)

echo.
echo Application ran successfully!
echo.

pause 