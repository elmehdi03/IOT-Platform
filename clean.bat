@echo off
REM =====================================================
REM Script de nettoyage complet de Docker
REM =====================================================

echo.
echo ========================================
echo   ATTENTION : Nettoyage complet
echo ========================================
echo.
echo Ce script va :
echo   - Arreter tous les conteneurs de l'application
echo   - Supprimer les images Docker de l'application
echo   - Nettoyer le cache Docker
echo.
echo Les donnees dans sensor_data.json seront CONSERVEES
echo.

set /p CONFIRM="Voulez-vous continuer ? (O/N) : "
if /i not "%CONFIRM%"=="O" (
    echo Operation annulee.
    pause
    exit /b 0
)

echo.
echo [1/4] Arret et suppression des conteneurs...
docker-compose down -v

echo.
echo [2/4] Suppression de l'image de l'application...
docker rmi iotplatform-iotplatform 2>nul
docker rmi iotplatform_iotplatform 2>nul

echo.
echo [3/4] Nettoyage du cache Docker...
docker builder prune -f

echo.
echo [4/4] Nettoyage des images non utilisees...
docker image prune -f

echo.
echo ========================================
echo   Nettoyage termine !
echo ========================================
echo.
echo Pour redemarrer l'application :
echo    start.bat
echo.

pause

