package org.aruba.login.service;

import org.aruba.login.notification.Notifica;
import org.aruba.login.notification.NotificationSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationSender implements NotificationSender
{
    @Autowired
    private JavaMailSender  javaMailSender;

    @Override
    public void sendNotification(final Notifica notifica)
    {
        // Implementazione specifica per l'invio di notifiche tramite email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(notifica.getDestinatario());
        message.setTo(notifica.getDestinatario());
        message.setSubject(notifica.getOggetto());
        message.setText(notifica.getTesto());
        javaMailSender.send(message);
    }
}


