@echo off
REM =====================================================
REM Script pour afficher les logs de l'application
REM =====================================================

echo.
echo ========================================
echo   Logs de la plateforme IoT
echo ========================================
echo.
echo Appuyez sur Ctrl+C pour quitter
echo.

docker-compose logs -f iotplatform

