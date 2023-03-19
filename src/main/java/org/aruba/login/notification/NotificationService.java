package org.aruba.login.notification;

import org.aruba.login.service.NotificationSenderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService
{

    @Autowired
    NotificationSenderFactory factory;

    public void sendNotification(final Notifica notifica) throws Exception
    {

        factory.createNotificationSender(notifica.getTipo()).sendNotification(notifica);

    }

}
