@echo off
REM =====================================================
REM Script de test de l'environnement Docker
REM =====================================================

echo.
echo ========================================
echo   Verification de l'environnement
echo ========================================
echo.

echo [1/4] Verification de Docker...
docker --version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [X] Docker n'est pas installe
    goto :error
) else (
    docker --version
    echo [OK] Docker est installe
)

echo.
echo [2/4] Verification que Docker est en cours d'execution...
docker info >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [X] Docker n'est pas en cours d'execution
    goto :error
) else (
    echo [OK] Docker est en cours d'execution
)

echo.
echo [3/4] Verification de Docker Compose...
docker-compose --version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [X] Docker Compose n'est pas disponible
    goto :error
) else (
    docker-compose --version
    echo [OK] Docker Compose est disponible
)

echo.
echo [4/4] Verification des fichiers necessaires...
if not exist "Dockerfile" (
    echo [X] Dockerfile manquant
    goto :error
)
echo [OK] Dockerfile present

if not exist "docker-compose.yml" (
    echo [X] docker-compose.yml manquant
    goto :error
)
echo [OK] docker-compose.yml present

if not exist "pom.xml" (
    echo [X] pom.xml manquant
    goto :error
)
echo [OK] pom.xml present

if not exist "src" (
    echo [X] Dossier src/ manquant
    goto :error
)
echo [OK] Dossier src/ present

echo.
echo ========================================
echo   Tous les tests sont passes !
echo ========================================
echo.
echo Vous pouvez maintenant lancer l'application avec :
echo    start.bat
echo.
goto :end

:error
echo.
echo ========================================
echo   Des erreurs ont ete detectees
echo ========================================
echo.
echo Veuillez corriger les problemes ci-dessus avant de continuer.
echo.

:end
pause

