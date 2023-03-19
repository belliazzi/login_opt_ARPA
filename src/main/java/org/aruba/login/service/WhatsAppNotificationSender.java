package org.aruba.login.service;

import org.aruba.login.notification.Notifica;
import org.aruba.login.notification.NotificationSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.rest.api.v2010.account.Message;

@Service
public class WhatsAppNotificationSender implements NotificationSender
{
    //@Autowired
    //private TwilioRestClient twilioRestClient;

    @Value("${twilio.phoneNumber}")
    private String fromPhoneNumber;

    @Override
    public void sendNotification(final Notifica notifica)
    {
        // Implementazione specifica per l'invio di notifiche tramite WhatsApp
        Message.creator(new com.twilio.type.PhoneNumber("whatsapp:+xxxxxxxxxxx"),
            new com.twilio.type.PhoneNumber("whatsapp:+14155238886"), "Hello there!").create();
    }
}
