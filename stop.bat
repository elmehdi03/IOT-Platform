@echo off
REM =====================================================
REM Script d'arrêt de la plateforme IoT
REM =====================================================

echo.
echo ========================================
echo   Arret de la plateforme IoT
echo ========================================
echo.

docker-compose down

if %ERRORLEVEL% EQU 0 (
    echo.
    echo [OK] Application arretee avec succes
) else (
    echo.
    echo [ERREUR] Echec de l'arret de l'application
)

echo.
pause

