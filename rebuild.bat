@echo off
REM =====================================================
REM Script de rebuild après modification du code
REM =====================================================

echo.
echo ========================================
echo   Rebuild de l'application
echo ========================================
echo.

echo Arret de l'application...
docker-compose down

echo.
echo Construction de la nouvelle version...
docker-compose build --no-cache

echo.
echo Demarrage de l'application...
docker-compose up -d

echo.
echo ========================================
echo   Rebuild termine !
echo ========================================
echo.
echo L'application sera disponible dans quelques instants...
echo URL : http://localhost:8080
echo.

pause

