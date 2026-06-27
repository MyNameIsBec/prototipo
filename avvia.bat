@echo off
set PATH=%LOCALAPPDATA%\apache-maven-3.9.9\bin;%PATH%
set TESSDATA_PREFIX=%LOCALAPPDATA%\Tesseract-OCR\tessdata
mvn javafx:run
pause
