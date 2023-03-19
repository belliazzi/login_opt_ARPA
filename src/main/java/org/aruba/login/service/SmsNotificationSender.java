package org.aruba.login.service;

import org.aruba.login.notification.Notifica;
import org.aruba.login.notification.NotificationSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsNotificationSender implements NotificationSender
{
   // @Autowired
   // private TwilioRestClient twilioRestClient;

    @Value("${twilio.phoneNumber}")
    private String fromPhoneNumber;

    @Override
    public void sendNotification(final Notifica notifica)
    {
        // Implementazione specifica per l'invio di notifiche tramite SMS
      //  Message message = 
            Message
            .creator(new PhoneNumber(notifica.getDestinatario()), new PhoneNumber(fromPhoneNumber), notifica.getTesto())
            .create();
       // twilioRestClient.messages().create(message);
    }
}
