@echo off
echo ========================================
echo VaultCard Backend Starting...
echo ========================================
echo.

REM 检查是否设置了 STRIPE_SECRET_KEY
if "%STRIPE_SECRET_KEY%"=="" (
    echo [ERROR] STRIPE_SECRET_KEY not set!
    echo.
    echo Please run:
    echo   set STRIPE_SECRET_KEY=sk_test_your_test_key
    echo.
    echo Then run this script again.
    pause
    exit /b 1
)

echo [OK] STRIPE_SECRET_KEY is set
echo.

echo Starting Spring Boot...
echo.

mvn spring-boot:run

pause
