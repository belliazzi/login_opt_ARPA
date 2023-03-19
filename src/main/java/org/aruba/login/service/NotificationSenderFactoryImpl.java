package org.aruba.login.service;

import org.aruba.login.notification.NotificationSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationSenderFactoryImpl implements NotificationSenderFactory
{

    @Autowired
    private EmailNotificationSender emailNotificationSender;

    @Autowired
    private WhatsAppNotificationSender whatsAppNotificationSender;

    @Autowired
    private SlackNotificationSender slackNotificationSender;

    @Autowired
    private SmsNotificationSender smsNotificationSender;

    @Override
    public NotificationSender createNotificationSender(final String type)
    {
        switch (type) {
            case "Email":
                return emailNotificationSender;
            case "Whatsapp":
                return whatsAppNotificationSender;
            case "Slack":
                return slackNotificationSender;
            case "Sms":
                return smsNotificationSender;
            default:
                return emailNotificationSender;
        }
    }
}
