echo Starting Maven project...
cd payment-processor
cd payment-processor
start mvn spring-boot:run

:: Wait a few seconds for Maven to initiate
timeout /t 5

:: Navigate to the Angular project folder
cd ..
cd ..
cd payment-processor-ui

:: Start Angular application
echo Starting Angular application...
start ng serve -o --proxy-config proxy.conf.json