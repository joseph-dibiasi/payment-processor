echo Starting Payment Processor Service...
cd payment-processor-service
start mvn spring-boot:run

:: Wait a few seconds for Maven to initiate
timeout /t 5

:: Navigate to the Payment Processor UI
cd ..
cd payment-processor-ui

:: Start Payment Processor UI
echo Starting Payment Processor UI...
start ng serve -o --proxy-config proxy.conf.json