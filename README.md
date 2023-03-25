# login_opt_registry_file
test login upload pdf , CF e otp , message broker 

Get started
primo step attivare Topic  kafka 
- istallare un kafka nella cartella c:/
   si consiglia kafka_2.12-2.8.0
   
   eseguire i seguneti comandi 
   - 1  cd C:\kafka_2.12-2.8.0\bin\windows
        
   - 2  kafka-server-start.bat C:\kafka_2.12-2.8.0\config\server.properties
    
   - 3 zookeeper-server-start.bat C:\kafka_2.12-2.8.0\config\zookeeper.properties
   
   - 4 creare un topic arubatopic 
       kafka-topics --zookeeper 127.0.0.1:2181 --topic arubatopic  --create --partitions 3 --replication-factor 1
       
 Ecco la sequenza dei comandi di Maven e Spring Boot per partire con il profilo "local":

Aprire un terminale o una finestra di comando nella cartella del progetto 

<b>mvn clean install</b> per pulire il progetto e generare un pacchetto di distribuzione

<b>mvn spring-boot:run -Dspring.profiles.active=local</b> per avviare l'applicazione Spring Boot con il profilo "local" attivato

<h3>setting delle properties</h3>

 <b>Data Source setting</b>
 - nel file application-local.properties 
   settare corretteamnte un data source effettivo esistente 
   si consiglia un nome di itsanza db otp_spring
   
 
  spring.datasource.driver-class-name=com.mysql.jdbc.Driver
  spring.datasource.jdbcUrl=jdbc:mysql://localhost:3306/otp_spring?serverTimezone=UTC&useLegacyDatetimeCode=false
  spring.datasource.username=******
  spring.datasource.password=********
  spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
  spring.jpa.properties.hibernate.jdbc.time_zone=UTC
  spring.jpa.hibernate.ddl-auto=none
  spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQLDialect
  spring.jpa.properties.hibernate.format_sql=true
  spring.jackson.serialization.fail-on-empty-beans=false
  
 
  <b>openkm 1.3 setting</b>
  
   se si dispone di un openkm 1.3 raggiungibile 
    settare i paramentri di connessione 
    nell application.local.propeorties
    
    - per open km 
    openkm.url=http://localhost:8080/OpenKM
    openkm.user=<vostro user admin>
    openkm.password=*******
    openkm.repository=/okm:root/TestFolder
    
    
    nel qual csao non fosse disponibile bastera settare in 
    application-local.properties 
    
    upload.filesystem.enable=true
    
    dove si spostera lo storage dei file nel vostro file sistem locale 
    nella cartella 
    upload.dir=C:/Users/username/fileStorage
    
    <h3>Per le notifiche email sms slack e whatzapp</h3>
    
    settare in application-local.properties  i dati di un vostro smtp reale effettivo
    
    - per email 

    spring.mail.host=
    spring.mail.port=
    spring.mail.username=
    spring.mail.password=
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true 
    spring.mail.properties.mail.smtp.trust=
    
    
    per comunicare con whatzapp e sms settare i dati di un accout twilio effettivo qui 
    - per whatzapp 
    twilio.accountSid=
    twilio.authToken=your_auth_token
    twilio.phoneNumber=
    - per slack 
    slack.webhookUrl=https://hooks.slack.com/services/T00000000/B00000000/XXXXXXXXXXXXXXXXXXXXXXXX
    slack.token=
    
    
    
    
  
