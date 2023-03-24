# login_opt_registry_file
test login upload pdf , CF e otp , message broker 

come partire 
 - nel file application-local.properties 
   settare corretteamnte un data source effettivo esistente 
   si consiglia un nome di itsanza db otp-spring
  
 - istallare un kafka nella cartella c:/
    
   se volete testare in locale 
   eseguite SpringBoot con profileo local 
   
   
   si consiglia kafka_2.12-2.8.0
   
   eseguire i seguneti comandi 
   - 1  cd C:\kafka_2.12-2.8.0\bin\windows
        
   - 2  kafka-server-start.bat C:\kafka_2.12-2.8.0\config\server.properties
    
   - 3 zookeeper-server-start.bat C:\kafka_2.12-2.8.0\config\zookeeper.properties
   
   - 4 creare un topic arubatopic 
       kafka-topics --zookeeper 127.0.0.1:2181 --topic arubatopic  --create --partitions 3 --replication-factor 1
       
      
   se si dispone di un openkm 1.3 raggiungibile 
    settare i paramentri di connessione 
    nell application.local.propeorties
    
    #per open km 
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
    
    <h3>Par le notifiche</h3>  
    settare in application-local.properties  i dati di un vostro smtp reale effettivo
    
    #per email 

    spring.mail.host=
    spring.mail.port=
    spring.mail.username=
    spring.mail.password=
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true 
    spring.mail.properties.mail.smtp.trust=
    
    
    per comunicare con wtahtzapp e sms settare i dati di un accout twilio effettivo qui 
    #per whatzapp 
    twilio.accountSid=
    twilio.authToken=your_auth_token
    twilio.phoneNumber=
    #per slack 
    slack.webhookUrl=https://hooks.slack.com/services/T00000000/B00000000/XXXXXXXXXXXXXXXXXXXXXXXX
    slack.token=
    
    
  
