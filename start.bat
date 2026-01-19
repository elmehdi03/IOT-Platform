@echo off
REM =====================================================
REM Script de lancement de la plateforme IoT
REM =====================================================

echo.
echo ========================================
echo   Demarrage de la plateforme IoT
echo ========================================
echo.

REM Vérifier si Docker est installé et en cours d'exécution
docker --version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [ERREUR] Docker n'est pas installe ou n'est pas dans le PATH.
    echo Veuillez installer Docker Desktop : https://www.docker.com/products/docker-desktop
    pause
    exit /b 1
)

REM Vérifier si Docker est en cours d'exécution
docker info >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [ERREUR] Docker n'est pas en cours d'execution.
    echo Veuillez demarrer Docker Desktop et reessayer.
    pause
    exit /b 1
)

echo [OK] Docker est installe et en cours d'execution
echo.

REM Créer le répertoire logs s'il n'existe pas
if not exist "logs" mkdir logs

REM Créer le fichier sensor_data.json s'il n'existe pas
if not exist "sensor_data.json" (
    echo [] > sensor_data.json
    echo [INFO] Fichier sensor_data.json cree
)

echo ========================================
echo   Arret des conteneurs existants...
echo ========================================
docker-compose down

echo.
echo ========================================
echo   Construction et demarrage...
echo ========================================
echo.
echo Cela peut prendre quelques minutes la premiere fois...
echo.

docker-compose up --build -d

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERREUR] Echec du demarrage de l'application
    pause
    exit /b 1
)

echo.
echo ========================================
echo   Application demarree avec succes !
echo ========================================
echo.
echo L'application sera disponible dans quelques secondes...
echo.
echo URL : http://localhost:8080
echo Dashboard : http://localhost:8080/iot-dashboard
echo.
echo Pour voir les logs en temps reel :
echo    docker-compose logs -f
echo.
echo Pour arreter l'application :
echo    docker-compose down
echo.
echo ========================================

REM Attendre que l'application soit prête
echo Verification du demarrage de l'application...
timeout /t 10 /nobreak >nul

:check_loop
set /a count=0
:retry
curl -s http://localhost:8080 >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo.
    echo [OK] L'application est prete !
    echo.
    echo Ouverture du navigateur...
    start http://localhost:8080
    goto :end
)

set /a count+=1
if %count% LSS 30 (
    timeout /t 2 /nobreak >nul
    goto :retry
)

echo.
echo [INFO] L'application est en cours de demarrage...
echo Veuillez patienter quelques instants puis ouvrir :
echo http://localhost:8080
echo.

:end
pause

